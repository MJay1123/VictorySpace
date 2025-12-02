<template>
    <div class="modal-overlay">
        <div class="modal">
            <button class="close-btn" @click="$emit('close')">X</button>
            <h2>새 투표 만들기</h2>

            <div>
                <label>제목:</label>
                <input v-model="title" type="text" />

                <label>내용:</label>
                <textarea v-model="content"></textarea>

                <label>기간:</label>
                <div class="duration">
                    <select v-model.number="durationValue">
                        <option v-for="n in 30" :key="n" :value="n">{{ n }}</option>
                    </select>
                    <select v-model="durationUnit">
                        <option value="h">hour</option>
                        <option value="d">day</option>
                    </select>
                </div>

                <button @click="createVote">생성</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue'

const title = ref('')
const content = ref('')
const durationValue = ref(1)
const durationUnit = ref('h')

const user = JSON.parse(localStorage.getItem('userInfo'))

const createVote = async () => {
    if (!title.value || !content.value || !duration.value) {
        return alert('모든 항목을 입력해주세요.')
    }
    const duration = `${durationValue.value}${durationUnit.value}`

    try {
        const res = await fetch('http://localhost:8080/api/vote', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                title: title.value,
                content: content.value,
                memberId: user.id,
                duration: duration.value
            })
        })

        if (!res.ok) {
            const msg = await res.text()
            console.error('생성 실패:', msg)
            return alert('투표 생성 실패!')
        }

        alert('투표가 생성되었습니다!')
        $emit('created')
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