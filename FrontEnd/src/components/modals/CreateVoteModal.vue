<template>
    <div class="modal-overlay">
        <div class="modal-card">

            <button class="close-btn" @click="$emit('close')">✕</button>

            <h2 class="modal-title">새 투표 만들기</h2>

            <div class="form">

                <div class="form-group">
                    <label>제목</label>
                    <input type="text" v-model="title" placeholder="제목을 입력하세요" />
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
                    <textarea v-model="content" placeholder="내용을 입력하세요"></textarea>
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

                <button class="action-btn" @click="createVote">투표 생성</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import categoryApi from '../../api/categoryApi';
import memberApi from '../../api/memberApi';
import voteApi from '../../api/voteApi';

const title = ref('');
const content = ref('');
const durationNumber = ref(null);
const durationUnit = ref('h');
const categories = ref([]);
const selectedCategoryId = ref('');

const user = JSON.parse(localStorage.getItem('userInfo'))
const emit = defineEmits(['created'])

// 카테고리 불러오기
onMounted(async () => {
    try {
        const res = await categoryApi.findAll();
        categories.value = await res.data;
    } catch (error) {
        console.error(error);
    }
});


const createVote = async () => {
    if (!title.value || !content.value || !durationNumber.value || !durationUnit.value || !selectedCategoryId.value) {
        alert('모든 항목을 입력해주세요');
        return;
    }

    const duration = `${durationNumber.value}${durationUnit.value}`

    const userDTO = await memberApi.findByEmail(user.email);
    const userId = userDTO.data.id;

    const voteDTO = {
        title: title.value,
        categoryId: selectedCategoryId.value,
        content: content.value,
        memberId: userId,
        duration: duration
    };

    try {
        await voteApi.createVote(voteDTO);
        alert("투표가 생성되었습니다!");
        emit("created");
    } catch (err) {
        console.error(err);
        alert("투표 생성 실패!");
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
    z-index: 900;
}

.modal-card {
    width: 520px;
    background: white;
    padding: 2rem;
    border-radius: 15px;
    box-shadow: 0 6px 25px rgba(0, 0, 0, 0.15);
    position: relative;
    animation: fadeIn 0.25s ease-out;
}

.close-btn {
    position: absolute;
    right: 15px;
    top: 15px;
    background: none;
    border: none;
    font-size: 20px;
    cursor: pointer;
    color: #777;
}

.modal-title {
    text-align: center;
    font-size: 1.75rem;
    font-weight: 600;
    margin-bottom: 1.2rem;
    color: #333;
}

.form {
    display: flex;
    flex-direction: column;
    gap: 1.3rem;
}

.form-group label {
    font-weight: 500;
    font-size: 0.95rem;
    color: #444;
}

input,
textarea,
select {
    width: 100%;
    padding: 10px 12px;
    border-radius: 10px;
    border: 2px solid #e0e0e0;
    font-size: 1rem;
    transition: all 0.25s;
}

input:focus,
textarea:focus,
select:focus {
    border-color: #667eea;
    outline: none;
    box-shadow: 0 0 6px rgba(102, 126, 234, 0.3);
}

textarea {
    min-height: 90px;
}

.duration {
    display: flex;
    flex-direction: column;
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
    transition: 0.25s;
}

.action-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 15px rgba(118, 75, 162, 0.3);
}

@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(-10px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}
</style>