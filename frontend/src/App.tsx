import { ChakraProvider } from '@chakra-ui/react'
import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
import { DashboardScreen } from './screens/DashboardScreen.tsx'

export default function App() {
  const queryClient = new QueryClient({
    defaultOptions: {
      queries: {
        staleTime: 60 * 1000 // 1 minute
      }
    }
  })

  return (
    <QueryClientProvider client={queryClient}>
      <ChakraProvider>
        <DashboardScreen />
      </ChakraProvider>
    </QueryClientProvider>
  )
}
