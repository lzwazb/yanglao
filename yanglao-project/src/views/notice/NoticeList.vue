<template>
  <div class="notice-list">
    <el-card>
      <template #header>
        <span>公告栏</span>
      </template>
      <el-scrollbar height="600px" v-loading="loading">
        <el-timeline v-if="notices.length > 0">
          <el-timeline-item
            v-for="notice in notices"
            :key="notice.id"
            :timestamp="notice.createTime"
            placement="top"
          >
            <el-card>
              <h4>{{ notice.title }}</h4>
              <p>{{ notice.content }}</p>
              <div class="notice-footer">
                <el-tag size="small" :type="getNoticeType(notice.type)">{{ notice.type }}</el-tag>
                <span class="notice-author">发布人：{{ notice.author }}</span>
              </div>
            </el-card>
          </el-timeline-item>
        </el-timeline>
        <el-empty v-else description="暂无公告" />
      </el-scrollbar>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getNoticeList } from '@/api/notice'

const notices = ref([])
const loading = ref(false)

const loadNotices = async () => {
  loading.value = true
  try {
    const res = await getNoticeList()
    notices.value = res
  } catch (error) {
    console.error('获取公告列表失败', error)
  } finally {
    loading.value = false
  }
}

const getNoticeType = (type) => {
  const map = {
    '重要': 'danger',
    '通知': 'primary',
    '活动': 'success'
  }
  return map[type] || 'info'
}

onMounted(() => {
  loadNotices()
})
</script>

<style scoped>
.notice-list {
  padding: 20px;
}

.notice-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 10px;
}

.notice-author {
  font-size: 12px;
  color: #999;
}
</style>
