import { useMutation } from '@tanstack/react-query'
import { useState } from 'react'
import {
  Button,
  Card,
  CardBody,
  CardHeader,
  Container,
  Flex,
  FormControl,
  FormLabel,
  Heading,
  Input
} from '@chakra-ui/react'
import { signin } from '../api/auth'
import { useToast } from '../hooks/useToast'

export function SignInScreen() {
  const [ email, setEmail ] = useState<string>('')
  const [ password, setPassword ] = useState<string>('')

  const toast = useToast()

  const signInMutation = useMutation({
    mutationKey: [ 'auth', 'signin' ],
    mutationFn: () => signin(email, password),
    onSuccess: () => toast.success("Success", "You've successfully logged in."),
    onError: () => toast.error("Failure", "Failed to log you in.")
  })

  return (
    <Container centerContent>
      <Card width="100%">
        <CardHeader>
          <Heading size="sm">Sign In</Heading>
        </CardHeader>
        <CardBody as={Flex} flexDirection="column" gap={8}>
          <FormControl isRequired>
            <FormLabel>Email</FormLabel>
            <Input
              value={email}
              type="email"
              onChange={e => setEmail(e.target.value)}
            />
          </FormControl>

          <FormControl isRequired>
            <FormLabel>Password</FormLabel>
            <Input
              value={password}
              type="password"
              onChange={e => setPassword(e.target.value)}
            />
          </FormControl>
          <Button onClick={() => signInMutation.mutate()}>Sign in</Button>
        </CardBody>
      </Card>
    </Container>
  )
}
