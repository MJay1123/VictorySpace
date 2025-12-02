<template>
    <div class="modal-overlay">
        <div class="modal">
            <button class="close-btn" @click="$emit('close')">X</button>
            <h2>새 투표 만들기</h2>

            <div class="form-group">
                <label for="title">제목</label>
                <input type="text" id="title" v-model="title" placeholder="제목을 입력하세요" />
            </div>

            <div class="form-group">
                <label for="category">카테고리</label>
                <select id="category" v-model="selectedCategoryId">
                    <option value="" disabled>카테고리를 선택하세요</option>
                    <option v-for="cat in categories" :key="cat.id" :value="cat.id">
                        {{ cat.name }}
                    </option>
                </select>
            </div>

            <div class="form-group">
                <label for="content">내용</label>
                <textarea id="content" v-model="content" placeholder="내용을 입력하세요"></textarea>
            </div>

            <div class="form-group duration">
                <label>기간</label>
                <select v-model="durationNumber">
                    <option v-for="n in 30" :key="n" :value="n">{{ n }}</option>
                </select>
                <select v-model="durationUnit">
                    <option value="h">hour</option>
                    <option value="d">day</option>
                </select>
            </div>

            <button @click="createVote">투표 생성</button>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

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
        const res = await fetch('http://localhost:8080/api/category');
        if (!res.ok) throw new Error('카테고리 불러오기 실패');
        categories.value = await res.json();
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

    try {
        const res = await fetch('http://localhost:8080/api/vote', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                title: title.value,
                categoryId: selectedCategoryId.value,
                content: content.value,
                memberId: user.id,
                duration: duration
            })
        })

        if (!res.ok) {
            const msg = await res.text()
            console.error('생성 실패:', msg)
            return alert('투표 생성 실패!')
        }

        alert('투표가 생성되었습니다!')
        emit('created')
    } catch (err) {
        console.error(err)
        alert('서버 오류!')
    }
}
</script>

<style scoped>
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
}

.modal {
    background: white;
    padding: 20px;
    border-radius: 8px;
    width: 400px;
}

.close-btn {
    float: right;
    background: red;
    color: white;
    border: none;
    cursor: pointer;
    padding: 5px 10px;
}

label {
    display: block;
    margin-top: 10px;
    font-weight: 500;
}

input,
textarea,
select {
    width: 100%;
    padding: 5px;
    margin-top: 5px;
    box-sizing: border-box;
}

.duration {
    display: flex;
    gap: 10px;
    align-items: center;
}

button {
    margin-top: 15px;
    padding: 5px 10px;
    cursor: pointer;
}
</style>