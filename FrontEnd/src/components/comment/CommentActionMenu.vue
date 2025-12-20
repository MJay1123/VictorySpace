<template>
    <div class="menu-wrapper">
        <button class="menu-btn" @click="toggle">
            ⋮
        </button>

        <div v-if="showMenu" class="menu-dropdown">
            <button @click="onEdit">✏️ 수정</button>
            <button @click="onDelete">🗑 삭제</button>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue'

const emit = defineEmits(['edit', 'delete'])

const showMenu = ref(false)

const toggle = () => {
    showMenu.value = !showMenu.value
}

const onEdit = () => {
    showMenu.value = false
    emit('edit')
}

const onDelete = () => {
    showMenu.value = false
    emit('delete')
}
</script>

<style scoped>
.vote-action-menu {
    display: flex;
    gap: 8px;
}

.menu-wrapper {
    position: relative;
}

.menu-btn {
    background: none;
    border: none;
    font-size: 26px;
    cursor: pointer;
    padding: 4px 8px;
}

.menu-dropdown {
    position: absolute;
    top: 36px;
    right: 0;
    background: white;
    border-radius: 10px;
    box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
    display: flex;
    flex-direction: column;
    min-width: 120px;
    overflow: hidden;
    z-index: 10;
}

.menu-dropdown button {
    padding: 14px 14px;
    border: none;
    background: none;
    cursor: pointer;
    text-align: middle;
    font-size: 20px;
}

.menu-dropdown button:hover {
    background: #f3f4f6;
}
</style>