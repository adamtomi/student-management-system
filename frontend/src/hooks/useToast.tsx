import { useToast as useChakraToast, UseToastOptions } from '@chakra-ui/react'
import { useCallback } from 'react'


interface UseToast {
  error: (title: string, description?: string, options?: UseToastOptions) => void
  success: (title: string, description?: string, options?: UseToastOptions) => void
}

const defaultOptions: UseToastOptions = { duration: 5000, position: 'bottom-left', isClosable: true }

export function useToast(): UseToast {
  const toast = useChakraToast()

  const error = useCallback((title: string, description?: string, options?: UseToastOptions) => {
    toast({ title, description, status: 'error', ...defaultOptions, ...options })
  }, [ toast ])

  const success = useCallback((title: string, description?: string, options?: UseToastOptions) => {
    toast({ title, description, status: 'success', ...defaultOptions, ...options })
  }, [ toast ])

  return { error, success }
}
