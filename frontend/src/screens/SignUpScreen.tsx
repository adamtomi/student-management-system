import { useMutation } from '@tanstack/react-query'
import { useCallback, useState } from 'react'
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
import { signup } from '../api/auth'
import { useToast } from '../hooks/useToast'

export function SignUpScreen() {
  const [ email, setEmail ] = useState<string>('')
  const [ password, setPassword ] = useState<string>('')
  const [ passwordConfirm, setPasswordConfirm ] = useState<string>('')
  const [ firstName, setFirstName ] = useState<string>('')
  const [ lastName, setLastName ] = useState<string>('')

  const toast = useToast()

  const signUpMutation = useMutation({
    mutationKey: [ 'auth', 'signup' ],
    mutationFn: () => signup({ email, password, firstName, lastName }),
    onSuccess: () => toast.success("Success", "You've successfully sign up."),
    onError: () => toast.error("Failure", "Failed to sign you up.")
  })

  const signUp = useCallback(() => {
    if (password !== passwordConfirm) {
      toast.error('Error', 'The provided passwords don\'t match.')
    } else {
      signUpMutation.mutate()
    }
  }, [ password, passwordConfirm, signUpMutation.mutate ])

  return (
    <Container centerContent>
      <Card width="100%">
        <CardHeader>
          <Heading size="sm">Sign In</Heading>
        </CardHeader>
        <CardBody as={Flex} flexDirection="column" gap={8}>
          <FormControl isRequired>
            <FormLabel>First name</FormLabel>
            <Input
              value={firstName}
              onChange={e => setFirstName(e.target.value)}
            />
          </FormControl>

          <FormControl isRequired>
            <FormLabel>Last name</FormLabel>
            <Input
              value={lastName}
              onChange={e => setLastName(e.target.value)}
            />
          </FormControl>

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

          <FormControl isRequired>
            <FormLabel>Password confirm</FormLabel>
            <Input
              value={passwordConfirm}
              type="password"
              onChange={e => setPasswordConfirm(e.target.value)}
            />
          </FormControl>
          <Button onClick={() => signUp()}>Sign up</Button>
        </CardBody>
      </Card>
    </Container>
  )
}
