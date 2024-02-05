import { doFetch, HttpMethod } from './api.ts'
import type { Student } from '../types'


export async function getStudents() {
  return doFetch<Student[]>({ endpoint: '/api/students' })
}

export async function findStudent(id: string) {
  return doFetch<Student>({ endpoint: `/api/students/${id}` })
}

export async function updateStudent(id: string, data: Partial<Student>) {
  return doFetch({ endpoint: `/api/students/${id}`, method: HttpMethod.POST, body: data })
}
