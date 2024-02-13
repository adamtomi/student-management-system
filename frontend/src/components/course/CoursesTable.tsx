import { useCallback, useEffect, useState, Fragment } from 'react'
import {
  Button,
  Card,
  CardBody,
  CardHeader,
  Heading,
  HStack,
  IconButton,
  Menu,
  MenuButton,
  MenuItem,
  MenuList,
  Spinner,
  Table,
  Thead,
  Tbody,
  Text,
  Tr,
  Th,
  Td
} from '@chakra-ui/react'
import { DotsThreeVertical, Plus } from 'phosphor-react'
import { useQuery } from '@tanstack/react-query'
import { getCourses } from '../../api/course'
import { AddCourseModal } from './AddCourseModal'
import { EditCourseModal } from './EditCourseModal'
import { EnrollmentsModal } from './EnrollmentsModal'

import type { Course } from '../../types'

enum Modal {
  AddCourse = 'AddCourse',
  EditCourse = 'EditCourse',
  EnrollStudents = 'EnrollStudents'
}

export function CoursesTable() {
  const [ courses, setCourses ] = useState<Course[]>([])
  const [ selectedCourse, setSelectedCourse ] = useState<Course | undefined>()
  const [ selectedModal, setSelectedModal ] = useState<Modal | undefined>()

  const getCoursesQuery = useQuery({
    queryKey: [ 'courses-get' ],
    queryFn: () => getCourses()
  })

  useEffect(() => {
    if (getCoursesQuery.isSuccess && getCoursesQuery.data.success) setCourses(getCoursesQuery.data.data)
  }, [ getCoursesQuery.isSuccess, getCoursesQuery.data?.success ])

  const openModal = useCallback((course: Course, modal: Modal) => {
    setSelectedCourse(course)
    setSelectedModal(modal)
  }, [ setSelectedCourse, setSelectedModal ])

  return (
    <Fragment>
      <Card width="100%">
        <CardHeader>
          <Heading size="md">
            <HStack gap={8}>
              <Text>Courses</Text>
              <Button onClick={() => setSelectedModal(Modal.AddCourse)}>
                <HStack gap={3}>
                  <Plus />
                  <Text>Add course</Text>
                </HStack>
              </Button>
            </HStack>
          </Heading>
        </CardHeader>
        <CardBody>
          {getCoursesQuery.isFetching
            ? <Spinner />
            : (
              <Table>
                <Thead>
                  <Tr>
                    <Th>ID</Th>
                    <Th>Name</Th>
                    <Th>Teacher name</Th>
                    <Th isNumeric></Th>
                  </Tr>
                </Thead>
                <Tbody>
                  {courses.map(course => (
                    <Tr key={course.id}>
                      <Td>{course.id}</Td>
                      <Td>{course.name}</Td>
                      <Td>{course.teacherName}</Td>
                      <Td isNumeric>
                        <Menu>
                          <MenuButton
                            title="More"
                            aria-label="More"
                            as={IconButton}
                            icon={<DotsThreeVertical />}
                          >
                          </MenuButton>
                          <MenuList>
                            <MenuItem
                              onClick={() => openModal(course, Modal.EditCourse)}
                            >
                              Edit course
                            </MenuItem>
                            <MenuItem
                              onClick={() => openModal(course, Modal.EnrollStudents)}
                            >
                              Enroll students
                            </MenuItem>
                          </MenuList>
                        </Menu>
                      </Td>
                    </Tr>
                  ))}
                </Tbody>
              </Table>
            )
          }
        </CardBody>
      </Card>

      {selectedModal === Modal.EditCourse && selectedCourse &&
        <EditCourseModal close={() => setSelectedModal(undefined)} course={selectedCourse} />
      }

      {selectedModal === Modal.EnrollStudents && selectedCourse &&
        <EnrollmentsModal close={() => setSelectedModal(undefined)} course={selectedCourse} />
      }

      {selectedModal === Modal.AddCourse &&
        <AddCourseModal close={() => setSelectedModal(undefined)} />
      }
    </Fragment>
  )
}
