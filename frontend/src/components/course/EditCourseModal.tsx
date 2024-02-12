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
import { updateCourse } from '../../api/course'
import { useToast } from '../../hooks/useToast'

import type { Course } from '../../types'

interface EditCourseModalProps {
  close: () => void
  course: Course
}

export function EditCourseModal({ close, course }: EditCourseModalProps) {
  const [ courseName, setCourseName ] = useState<string>(course.name)
  const [ teacherName, setTeacherName ] = useState<string>(course.teacherName)

  const toast = useToast()

  const updateCourseMutation = useMutation({
    mutationKey: [ 'course', course.id, 'update' ],
    mutationFn: () => updateCourse(course.id, {
      name: courseName,
      teacherName
    }),
    onError: () => toast.error("Failure", "Failed to update course data."),
    onSuccess: () => {
      close()
      toast.success("Success", "Successfully updated course data.")
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

            <FormLabel>Teacher name</FormLabel>
            <Input
              value={teacherName}
              onChange={e => setTeacherName(e.target.value)}
            />
          </FormControl>
        </ModalBody>

        <ModalFooter gap={4}>
          <Button variant="ghost" onClick={close}>Close</Button>
          <Button onClick={() => updateCourseMutation.mutate()}>Submit</Button>
        </ModalFooter>
      </ModalContent>
    </Modal>
  )
}
