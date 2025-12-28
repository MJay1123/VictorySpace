<template>
  <div class="tree-wrapper">
    <div class="controls">
      <button @click="bulbs = !bulbs">💡 전구</button>
      <button @click="snow = !snow">❄️ 눈</button>
      <button @click="star = !star">⭐ 별</button>
    </div>

    <canvas ref="canvas" width="500" height="600"></canvas>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'

const canvas = ref(null)

const bulbs = ref(true)
const snow = ref(true)
const star = ref(true)

let ctx
let snowflakes = []

onMounted(() => {
  ctx = canvas.value.getContext('2d')
  initSnow()
  animate()
})

watch([bulbs, snow, star], () => {
  draw()
})

/* -------------------- DRAW -------------------- */

function draw() {
  ctx.clearRect(0, 0, 500, 600)

  // background
  ctx.fillStyle = '#000'
  ctx.fillRect(0, 0, 500, 600)

  drawTrunk()
  drawTree()

  if (bulbs.value) drawBulbs()
  if (star.value) drawStar()
}

function drawTrunk() {
  ctx.fillStyle = 'brown'
  ctx.fillRect(240, 460, 20, 90)
}

function drawTree() {
  for (let y = 0; y < 420; y += 3) {
    const width = (420 - y) * 0.6
    for (let x = -width; x < width; x += 4) {
      const green = 120 + Math.random() * 80
      ctx.fillStyle = `rgb(0, ${green}, 0)`
      ctx.fillRect(250 + x, 460 - y, 3, 3)
    }
  }
}

/* -------------------- BULBS -------------------- */

function drawBulbs() {
  const colors = ['red', 'gold', 'deepskyblue', 'lime']
  for (let i = 0; i < 20; i++) {
    ctx.beginPath()
    ctx.fillStyle = colors[Math.floor(Math.random() * colors.length)]
    ctx.arc(
      250 + (Math.random() - 0.5) * 300,
      150 + Math.random() * 300,
      5,
      0,
      Math.PI * 2
    )
    ctx.fill()
  }
}

/* -------------------- STAR -------------------- */

function drawStar() {
  ctx.font = '50px serif'
  ctx.fillText('⭐', 225, 60)
}

/* -------------------- SNOW -------------------- */

function initSnow() {
  snowflakes = Array.from({ length: 80 }, () => ({
    x: Math.random() * 500,
    y: Math.random() * 600,
    size: Math.random() * 2 + 1,
    speed: Math.random() * 1 + 0.5
  }))
}

function drawSnow() {
  ctx.font = '14px serif'
  ctx.fillStyle = 'white'
  snowflakes.forEach(flake => {
    ctx.fillText('❄️', flake.x, flake.y)
    flake.y += flake.speed
    if (flake.y > 600) flake.y = -10
  })
}

/* -------------------- ANIMATION -------------------- */

function animate() {
  draw()
  if (snow.value) drawSnow()
  requestAnimationFrame(animate)
}
</script>

<style scoped>
.tree-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
}

.controls {
  display: flex;
  gap: 8px;
}

button {
  padding: 6px 12px;
  border-radius: 6px;
  border: none;
  cursor: pointer;
  font-weight: 700;
  background: #1f2937;
  color: #fff;
}

canvas {
  border-radius: 12px;
}
</style>
