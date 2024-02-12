import { doFetch, HttpMethod } from './api.ts'

export async function signin(email: string, password: string) {
  const formData = new FormData();
  formData.set('username', email)
  formData.set('password', password)

  return doFetch({ endpoint: '/login', method: HttpMethod.POST, body: formData })
}
