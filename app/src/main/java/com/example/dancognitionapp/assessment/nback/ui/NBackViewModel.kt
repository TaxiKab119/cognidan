package com.example.dancognitionapp.assessment.nback.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import com.example.dancognitionapp.assessment.nback.data.NBackClickCategorization
import com.example.dancognitionapp.assessment.nback.data.NBackGenerator
import com.example.dancognitionapp.assessment.nback.data.NBackItem
import com.example.dancognitionapp.assessment.nback.data.NBackType
import com.example.dancognitionapp.assessment.nback.db.NBackEntity
import com.example.dancognitionapp.assessment.nback.db.NBackItemEntity
import com.example.dancognitionapp.assessment.nback.db.NBackRepository
import com.example.dancognitionapp.participants.db.Participant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class NBackViewModel(
    private val nBackRepository: NBackRepository,
    isPractice: Boolean,
): ViewModel() {
    private val presentationOrders = mutableListOf(
        NBackGenerator(testType = NBackType.N_1).items,
        NBackGenerator(testType = NBackType.N_2).items,
        NBackGenerator(testType = NBackType.N_3).items
    )
    private var testType = NBackType.N_1
    private var lifetimePresentations = 0
    private var blockNumber = 1
    // 6 = 2 blocks = Real Assessment; 3 = 1 Block = Practice
    private var maxLifetimePresentations = if (isPractice) 3 else 6

    private val _uiState = MutableStateFlow(
        NBackUiState(
            presentationList = presentationOrders[0],
            isPractice = isPractice
        )
    )
    val uiState: StateFlow<NBackUiState> = _uiState.asStateFlow()

    private val currentState: NBackUiState
        get() = _uiState.value

    private lateinit var currentNBackEntity: NBackEntity

    fun initNBackTrial(participant: Participant, trialDay: TrialDay, trialTime: TrialTime) {
        // initialize a trial and add it to the database
        viewModelScope.launch(Dispatchers.IO) {
            currentNBackEntity = NBackEntity(
                participantId = participant.id,
                trialDay = trialDay,
                trialTime = trialTime,
                userGivenParticipantId = participant.userGivenId
            )
            async {
                nBackRepository.insertNBackTrial(currentNBackEntity)
            }.await()
            async {
                currentNBackEntity = currentNBackEntity.copy(
                    id = nBackRepository.getNBackEntityForTrial(
                        participantId = participant.id,
                        trialDay = trialDay,
                        trialTime = trialTime
                    )?.id ?: 0
                )
            }
        }
    }
    fun startAdvancing() {
        hideDialog()
        viewModelScope.launch {
            delay(2000)
            while (!currentState.presentationList.isEmpty()) {
                toNextItem()
                toggleScreenClickable()
                Timber.i("Current Char: ${uiState.value.currentItem} and list Size: ${uiState.value.presentationList.size}")
                val startShowingStimulusTime = System.currentTimeMillis()
                delay(1500) // Show Stimulus for 1500ms
                Timber.i("Current Char (AFTER DELAY): ${uiState.value.currentItem} and list Size: ${uiState.value.presentationList.size}")
                val clickTime = _uiState.value.clickTime
                var reactionTime: Long? = clickTime - startShowingStimulusTime
                if (clickTime == 0L) {
                    reactionTime = null
                }
                val uiStateCopy: NBackUiState = currentState.copy()
                Timber.i("Current Char (AFTER DELAY, IN IF STATEMENT): ${uiState.value.currentItem}")
                viewModelScope.launch(Dispatchers.IO) {
                    Timber.i("Current Char (AFTER DELAY, IN IF launch - ITEM COPY): ${uiStateCopy.currentItem}")
                    Timber.i("Current Char (AFTER DELAY, IN IF launch): ${uiState.value.currentItem}")
                    nBackRepository.insertNBackItem(uiStateCopy.toNBackItemEntity(reactionTime))
                }
                _uiState.value = currentState.copy(
                    currentItem = NBackItem.intermediateItem,
                    feedbackState = NBackFeedbackState.INTERMEDIATE,
                    clickTime = 0L
                )
                toggleScreenClickable()

                if (uiState.value.isPractice && reactionTime == null && uiStateCopy.currentItem.isTarget()) {
                    toggleFalseAlarm() // show dialog
                    delay(1000)
                    toggleFalseAlarm() // hide dialog
                }
                delay(500) // inter-stimulus time (no dot showing)
            }
            delay(1000) // add a delay before showing dialog for next type
            toNextList()
        }
    }

    private fun toggleFalseAlarm() {
        _uiState.update {
            it.copy(
                isFalseAlarm = !uiState.value.isFalseAlarm
            )
        }
    }
    private fun hideDialog() {
        _uiState.value = currentState.copy(
            showDialog = !currentState.showDialog
        )
    }

    fun participantClick(currentItem: NBackItem, clickTime: Long) {
        _uiState.value = currentState.copy(
            feedbackState = if(currentItem.isTarget()) NBackFeedbackState.HIT
            else NBackFeedbackState.FALSE_ALARM,
            hasUserClicked = true,
            clickTime = clickTime
        )
        Timber.i("User Clicked Screen")
    }
    private fun toggleScreenClickable() {
        _uiState.value = currentState.copy(
            isTestScreenClickable = !currentState.isTestScreenClickable,
            hasUserClicked = false
        )
    }

    private fun toNextItem() {
        if (currentState.presentationList.isEmpty()) {
            toNextList()
            return
        }
        _uiState.value = currentState.copy(
            presentationList = currentState.presentationList,
            currentItem = currentState.presentationList.pop()
        )
    }

    private fun toNextList() {
        lifetimePresentations++
        if (testType.value == 3 && lifetimePresentations < maxLifetimePresentations) {
            regeneratePresentationOrders()
            blockNumber++
            testType = NBackType.N_1
            _uiState.value = currentState.copy(
                presentationList = presentationOrders[0],
                nValue = testType,
                showDialog = true
            )
        } else if (lifetimePresentations < maxLifetimePresentations) {
            testType = NBackType.values()[testType.ordinal + 1]
            _uiState.value = currentState.copy(
                presentationList = presentationOrders[testType.ordinal],
                showDialog = true,
                nValue = testType,
            )
            Timber.i("${currentState.presentationList}")
        } else {
            _uiState.value = currentState.copy(
                isEndOfTest = true
            )
        }
    }
    private fun regeneratePresentationOrders() {
        presentationOrders[0] = NBackGenerator(testType = NBackType.N_1).items
        presentationOrders[1] = NBackGenerator(testType = NBackType.N_2).items
        presentationOrders[2] = NBackGenerator(testType = NBackType.N_3).items
    }

    private fun NBackUiState.toNBackItemEntity(reactionTime: Long?): NBackItemEntity {
        val currentItem: NBackItem = this.currentItem
        val isTarget: Boolean = currentItem.isTarget()
        val categorization: NBackClickCategorization = categorizeClick(reactionTime, isTarget)
        val isCorrect = categorization == NBackClickCategorization.HIT
                || categorization == NBackClickCategorization.CORRECT_REJECTION

        return NBackItemEntity(
            nBackEntityId = currentNBackEntity.id,
            nValue = this.nValue.value,
            blockNumber = blockNumber,
            position = currentItem.position,
            isTarget = isTarget,
            reactionTime = reactionTime,
            categorization = categorization,
            wasCorrectAction = isCorrect,
        )
    }

    private fun categorizeClick(
        reactionTime: Long?,
        isTarget: Boolean
    ): NBackClickCategorization {
        return when {
            reactionTime != null && isTarget -> NBackClickCategorization.HIT
            reactionTime !=null && !isTarget -> NBackClickCategorization.FALSE_ALARM
            reactionTime == null && isTarget -> NBackClickCategorization.MISS
            else -> NBackClickCategorization.CORRECT_REJECTION
        }
    }
}