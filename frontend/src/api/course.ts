import { doFetch, HttpMethod } from './api.ts'
import type { Course } from '../types'

export async function getCourses() {
  return doFetch<Course[]>({ endpoint: '/api/courses' })
}
