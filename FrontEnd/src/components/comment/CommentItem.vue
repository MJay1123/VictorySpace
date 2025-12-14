<template>
    <div class="comment-item">
        <div class="meta">
            <b>{{ comment.nickname ?? '익명' }}</b>
            <span class="date">{{ formatDate(comment.createdAt) }}</span>
        </div>
        <p class="content">{{ comment.content }}</p>
    </div>
</template>

<script setup>
defineProps({
    comment: Object,
})

const formatDate = (dateString) => {
    if (!dateString) return ''
    const d = new Date(dateString)
    return `${d.getMonth() + 1}/${d.getDate()} ${d.getHours()}:${String(d.getMinutes()).padStart(2, '0')}`
}
</script>

<style scoped>
.comment-item {
    padding: 12px 0;
    border-bottom: 1px solid #eee;
}

.meta {
    font-size: 13px;
    color: #555;
    display: flex;
    justify-content: space-between;
}

.content {
    margin-top: 6px;
    font-size: 14px;
}
</style>