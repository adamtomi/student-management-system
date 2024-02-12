import { ChakraProvider } from '@chakra-ui/react'
import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import { DashboardScreen } from './screens/DashboardScreen'
import { SignInScreen } from './screens/SignInScreen'
import { SignUpScreen } from './screens/SignUpScreen'

function Navitagion() {
  return (
    <BrowserRouter basename="/">
      <Routes>
        <Route path="/" element={<DashboardScreen />} />
        <Route path="/signin" element={<SignInScreen />} />
        <Route path="/signup" element={<SignUpScreen />} />
        <Route path="*" element={<Navigate to="/" />} />
      </Routes>
    </BrowserRouter>
  )
}

export default function App() {
  const queryClient = new QueryClient({
    defaultOptions: {
      queries: {
        staleTime: 60 * 1000, // 1 minute
        retry: 2
      }
    }
  })

  return (
    <QueryClientProvider client={queryClient}>
      <ChakraProvider>
        <Navitagion />
      </ChakraProvider>
    </QueryClientProvider>
  )
}
