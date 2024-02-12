import { useMutation } from '@tanstack/react-query'
import { useState } from 'react'
import { signin } from '../api/auth'
import { useToast } from '../hooks/useToast'

export function SignInScreen() {
  const [ email, setEmail ] = useState<string>('')
  const [ password, setPassworf ] = useState<string>('')

  const toast = useToast()

  const signInMutation = useMutation({
    mutationKey: [ 'auth', 'signin' ],
    mutationFn: () => signin(email, password),
    onSuccess: () => toast.success("Success", "You've successfully logged in."),
    onError: () => toast.error("Failure", "Failed to log you in.")
  })

  return (
    <div></div>
  )
}
