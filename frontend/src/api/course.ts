import { doFetch, HttpMethod } from './api'
import type { Course } from '../types'

export async function getCourses() {
  return doFetch<Course[]>({ endpoint: '/api/courses' })
}

export async function updateCourse(id: string, data: Partial<Course>) {
  return doFetch({ endpoint: `/api/courses/${id}`, method: HttpMethod.POST, body: data })
}
