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
import { createCourse } from '../../api/course'
import { useToast } from '../../hooks/useToast'


interface AddCourseModalProps {
  close: () => void
}

export function AddCourseModal({ close }: AddCourseModalProps) {
  const [ courseName, setCourseName ] = useState<string>('')
  const [ teacherName, setTeacherName ] = useState<string>('')

  const toast = useToast()

  const addCourseMutation = useMutation({
    mutationKey: [ 'course-add' ],
    mutationFn: () => createCourse({ name: courseName, teacherName }),
    onError: () => toast.error('Failure', 'Failed to create course.'),
    onSuccess: () => {
      close()
      toast.success('Success', 'Successfully created a new course.')
    }
  })

  return (
    <Modal isOpen onClose={close} isCentered>
      <ModalOverlay />
      <ModalContent>
        <ModalHeader>Edit course</ModalHeader>
        <ModalCloseButton />

        <ModalBody as={Flex} gap={4} direction="column">
          <FormControl>
            <FormLabel>Course name</FormLabel>
            <Input
              value={courseName}
              onChange={e => setCourseName(e.target.value)}
            />
          </FormControl>

          <FormControl>
            <FormLabel>Teacher name</FormLabel>
            <Input
              value={teacherName}
              onChange={e => setTeacherName(e.target.value)}
            />
          </FormControl>

        </ModalBody>

        <ModalFooter gap={4}>
          <Button variant="ghost" onClick={close}>Close</Button>
          <Button onClick={() => addCourseMutation.mutate()}>Submit</Button>
        </ModalFooter>
      </ModalContent>
    </Modal>
  )
}
