<template>
    <div class="modal-overlay">
        <div class="modal-card">
            <h3>투표를 삭제하시겠습니까?</h3>

            <div class="actions">
                <button class="yes" @click="deleteVote">예</button>
                <button class="no" @click="$emit('close')">아니오</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import voteApi from '../../api/voteApi';

const props = defineProps({
    voteId: Number
});
const emit = defineEmits(['deleted', 'close']);

const deleteVote = async () => {
    await voteApi.deleteVote(props.voteId);
    emit('deleted');
    emit('close');
};
</script>
