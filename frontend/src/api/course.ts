import { doFetch, HttpMethod } from './api'
import type { Course } from '../types'

export async function getCourses() {
  return doFetch<Course[]>({ endpoint: '/api/courses' })
}

export async function updateCourse(id: string, data: Partial<Course>) {
  return doFetch({ endpoint: `/api/courses/${id}`, method: HttpMethod.POST, body: data })
}

export async function enrollToCourse(courseId: string, userId: string) {
  return doFetch({ endpoint: `/api/courses/${courseId}/enroll/${userId}`, method: HttpMethod.POST })
}

export async function createCourse(data: Omit<Course, 'id' | 'students'>) {
  return doFetch({ endpoint: `/api/courses`, method: HttpMethod.PUT, body: data })
}
