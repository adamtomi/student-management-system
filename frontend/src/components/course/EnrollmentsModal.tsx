import { useState } from 'react'
import {
  Button,
  Flex,
  FormControl,
  FormLabel,
  Input,
  List,
  ListItem,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay
} from '@chakra-ui/react'
import { useMutation } from '@tanstack/react-query'
import { enrollToCourse } from '../../api/course'
import { useToast } from '../../hooks/useToast'

import type { Course } from '../../types'

interface EditCourseModalProps {
  close: () => void
  course: Course
}

export function EnrollmentsModal({ course, close }: EditCourseModalProps) {
  const [ studentId, setStudentId ] = useState<string>('')

  const toast = useToast()

  const enrollToCourseMutation = useMutation({
    mutationKey: [ 'course', course.id, 'enroll' ],
    mutationFn: () => enrollToCourse(course.id, studentId),
    onSuccess: () => {
      close()
      toast.success('Success', 'The student was successfully enrolled to this course.')
    },
    onError: () => toast.error('Failure', 'Failed to enroll student to this course.')
  })

  return (
    <Modal isOpen onClose={close} isCentered>
      <ModalOverlay />
      <ModalContent>
        <ModalHeader>Enrolled students</ModalHeader>
        <ModalCloseButton />

        <ModalBody as={Flex} gap={4} direction="column">
          <List spacing={3}>
            {course.students.map(x => (
              <ListItem key={x}>
                {x}
              </ListItem>
            ))}
          </List>

          <FormControl>
            <FormLabel>Student ID</FormLabel>
            <Input
              value={studentId}
              onChange={e => setStudentId(e.target.value)}
            />
          </FormControl>
        </ModalBody>

        <ModalFooter gap={4}>
          <Button variant="ghost" onClick={close}>Close</Button>
          <Button onClick={() => enrollToCourseMutation.mutate()}>Submit</Button>
        </ModalFooter>
      </ModalContent>
    </Modal>
  )
}
