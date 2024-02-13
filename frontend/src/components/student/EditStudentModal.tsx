import { useState } from 'react'
import {
  Button,
  Flex,
  FormControl,
  FormLabel,
  Input,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay
} from '@chakra-ui/react'
import { useMutation } from '@tanstack/react-query'
import { updateStudent } from '../../api/student'
import { useToast } from '../../hooks/useToast'

import type { Student } from '../../types'

interface EditStudentModalProps {
  close: () => void
  student: Student
}

export function EditStudentModal({ close, student }: EditStudentModalProps) {
  const [ firstName, setFirstName ] = useState<string>(student.firstName)
  const [ lastName, setLastName ] = useState<string>(student.lastName)
  const [ email, setEmail ] = useState<string>(student.email)

  const toast = useToast()

  const updateStudentMutation = useMutation({
    mutationKey: [ 'student', student.id, 'update' ],
    mutationFn: () => updateStudent(student.id, {
      firstName,
      lastName,
      email
    }),
    onError: () => toast.error('Failure', 'Failed to update student data.'),
    onSuccess: () => {
      close()
      toast.success('Success', 'Successfully updated student data.')
    }
  })

  return (
    <Modal isOpen onClose={close} isCentered>
      <ModalOverlay />
      <ModalContent>
        <ModalHeader>Edit student</ModalHeader>
        <ModalCloseButton />

        <ModalBody as={Flex} gap={4} direction="column">
          <FormControl>
            <FormLabel>First name</FormLabel>
            <Input
              value={firstName}
              onChange={e => setFirstName(e.target.value)}
            />

            <FormLabel>Last name</FormLabel>
            <Input
              value={lastName}
              onChange={e => setLastName(e.target.value)}
            />
          </FormControl>

          <FormControl>
            <FormLabel>Email</FormLabel>
            <Input
              value={email}
              onChange={e => setEmail(e.target.value)}
            />
          </FormControl>

        </ModalBody>

        <ModalFooter gap={4}>
          <Button variant="ghost" onClick={close}>Close</Button>
          <Button onClick={() => updateStudentMutation.mutate()}>Submit</Button>
        </ModalFooter>
      </ModalContent>
    </Modal>
  )
}
