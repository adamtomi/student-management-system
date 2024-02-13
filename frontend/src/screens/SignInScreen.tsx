import { useMutation } from '@tanstack/react-query'
import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
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
  Input,
  Spinner,
  Text
} from '@chakra-ui/react'
import { signin } from '../api/auth'

export function SignInScreen() {
  const [ email, setEmail ] = useState<string>('')
  const [ password, setPassword ] = useState<string>('')

  const navigate = useNavigate()

  const signInMutation = useMutation({
    mutationKey: [ 'auth', 'signin' ],
    mutationFn: () => signin(email, password),
    onSettled: () => navigate('/', { replace: true })
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
          <Button onClick={() => signInMutation.mutate()} disabled={signInMutation.isPending}>
            {signInMutation.isPending
              ? <Spinner />
              : <Text>Sign in</Text>
            }
          </Button>
        </CardBody>
      </Card>
    </Container>
  )
}
