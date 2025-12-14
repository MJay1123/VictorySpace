<template>
    <div class="modal-overlay">
        <div class="modal-card">

            <button class="close-btn" @click="$emit('close')">✕</button>

            <h2 class="modal-title">투표 수정</h2>

            <div class="form">
                <div class="form-group">
                    <label>제목</label>
                    <input type="text" v-model="title" />
                </div>

                <div class="form-group">
                    <label>카테고리</label>
                    <select v-model="selectedCategoryId">
                        <option disabled value="">카테고리를 선택하세요</option>
                        <option v-for="cat in categories" :key="cat.id" :value="cat.id">
                            {{ cat.name }}
                        </option>
                    </select>
                </div>

                <div class="form-group">
                    <label>내용</label>
                    <textarea v-model="content"></textarea>
                </div>

                <div class="form-group duration">
                    <label>기간</label>
                    <div class="duration-select">
                        <select v-model="durationNumber">
                            <option v-for="n in 30" :key="n" :value="n">{{ n }}</option>
                        </select>
                        <select v-model="durationUnit">
                            <option value="h">시간</option>
                            <option value="d">일</option>
                        </select>
                    </div>
                </div>

                <button class="action-btn" @click="updateVote">수정하기</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import categoryApi from '../../api/categoryApi';
import voteApi from '../../api/voteApi';

const props = defineProps({
    vote: Object
});

const emit = defineEmits(['updated', 'close']);

const title = ref('');
const content = ref('');
const durationNumber = ref(null);
const durationUnit = ref('h');
const categories = ref([]);
const selectedCategoryId = ref('');

onMounted(async () => {
    // 기존 투표 데이터 세팅
    title.value = props.vote.title;
    content.value = props.vote.content;
    selectedCategoryId.value = props.vote.categoryId;

    if (props.vote.duration) {
        durationNumber.value = parseInt(props.vote.duration);
        durationUnit.value = props.vote.duration.endsWith('d') ? 'd' : 'h';
    }

    const res = await categoryApi.findAll();
    categories.value = res.data;
});

const updateVote = async () => {
    if (!title.value || !content.value || !selectedCategoryId.value) {
        alert('모든 항목을 입력해주세요');
        return;
    }

    const voteDTO = {
        title: title.value,
        content: content.value,
        categoryId: selectedCategoryId.value,
        duration: `${durationNumber.value}${durationUnit.value}`
    };

    try {
        await voteApi.updateVote(props.vote.id, voteDTO);
        alert('투표가 수정되었습니다!');
        emit('updated');
        emit('close');
    } catch (e) {
        console.error(e);
        alert('투표 수정 실패');
    }
};
</script>

<style scoped>
.modal-overlay {
    position: fixed;
    inset: 0;
    background: rgba(0, 0, 0, 0.55);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1200;
}

.modal-card {
    width: 520px;
    background: white;
    padding: 2rem;
    border-radius: 15px;
    box-shadow: 0 6px 25px rgba(0, 0, 0, 0.15);
    position: relative;
}

.close-btn {
    position: absolute;
    right: 15px;
    top: 15px;
    background: none;
    border: none;
    font-size: 20px;
    cursor: pointer;
}

.modal-title {
    text-align: center;
    font-size: 1.7rem;
    font-weight: 600;
    margin-bottom: 1.2rem;
}

.form {
    display: flex;
    flex-direction: column;
    gap: 1.3rem;
}

input,
textarea,
select {
    width: 100%;
    padding: 10px 12px;
    border-radius: 10px;
    border: 2px solid #e0e0e0;
}

textarea {
    min-height: 90px;
}

.duration-select {
    display: flex;
    gap: 10px;
}

.action-btn {
    margin-top: 10px;
    padding: 14px;
    font-size: 1.05rem;
    font-weight: 600;
    color: white;
    background: linear-gradient(135deg, #667eea, #764ba2);
    border: none;
    border-radius: 10px;
    cursor: pointer;
}
</style>