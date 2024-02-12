import { useState } from 'react'
import {
  Button,
  Flex,
  HStack,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay,
  Text
} from '@chakra-ui/react'
import type { Course } from '../../types'

interface EditCourseModalProps {
  close: () => void
  course: Course
}

export function EditCourseModal({ close, course }: EditCourseModalProps) {
  const [ courseName, setCourseName ] = useState<string>(course.name)
  const [ teacherName, setTeacherName ] = useState<string>(course.teacherName)

  return (
    <Modal isOpen onClose={close} isCentered>
      <ModalOverlay />
      <ModalContent>
        <ModalHeader>Edit course</ModalHeader>
        <ModalCloseButton />
        <ModalBody as={Flex} gap={4} direction="column">

        </ModalBody>
        <ModalFooter gap={4}>
          <Button variant="ghost" onClick={close}>Close</Button>
          <Button>Submit</Button>
        </ModalFooter>
      </ModalContent>
    </Modal>
  )
}
