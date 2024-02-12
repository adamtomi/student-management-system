import { doFetch, HttpMethod } from './api'
import type { Student } from '../types'

export async function signin(email: string, password: string) {
  const formData = new FormData();
  formData.set('username', email)
  formData.set('password', password)

  return doFetch({ endpoint: '/api/auth/signin', method: HttpMethod.POST, body: formData })
}

export async function signup(data: Omit<Student, 'id'> & { password: string }) {
  return doFetch({ endpoint: '/api/auth/signup', method: HttpMethod.POST, body: data })
}
