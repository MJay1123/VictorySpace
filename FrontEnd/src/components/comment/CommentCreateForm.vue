<template>
    <div class="comment-form">
      <textarea
        v-model="content"
        placeholder="댓글을 입력하세요"
        rows="3"
      />
  
      <button @click="submitComment">등록</button>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import commentApi from '@/api/commentApi'
  
  const props = defineProps({
    voteId: Number,
  })
  
  const emit = defineEmits(['created'])
  
  const content = ref('')
  
  const submitComment = async () => {
    if (!content.value.trim()) return
  
    try {
      await commentApi.create({
        voteId: props.voteId,
        content: content.value,
      })
  
      content.value = ''
      emit('created')
    } catch (e) {
      console.error('댓글 작성 실패', e)
    }
  }
  </script>
  
  <style scoped>
  .comment-form {
    display: flex;
    flex-direction: column;
    gap: 8px;
    margin-bottom: 20px;
  }
  
  textarea {
    resize: none;
    padding: 10px;
    border-radius: 6px;
    border: 1px solid #d1d5db;
  }
  
  button {
    align-self: flex-end;
    background: #2563eb;
    color: white;
    padding: 6px 14px;
    border-radius: 6px;
    border: none;
    cursor: pointer;
  }
  </style>
  