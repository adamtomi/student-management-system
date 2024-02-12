import { useCallback, useEffect, useState, Fragment } from 'react'
import {
  Card,
  CardBody,
  CardHeader,
  Heading,
  Spinner,
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td
} from '@chakra-ui/react'
import { useQuery } from '@tanstack/react-query'
import { getCourses } from '../../api/course.ts'
import { EditCourseModal } from './EditCourseModal.tsx'

import type { Course } from '../../types'

export function CoursesTable() {
  const [ courses, setCourses ] = useState<Course[]>([])
  const [ selectedCourse, setSelectedCourse ] = useState<Course | undefined>()
  const [ isEditCourseModalOpen, setEditCourseModalOpen ] = useState<boolean>(false)

  const getCoursesQuery = useQuery({
    queryKey: [ 'courses-get' ],
    queryFn: () => getCourses()
  })

  useEffect(() => {
    if (getCoursesQuery.isSuccess && getCoursesQuery.data.success) setCourses(getCoursesQuery.data.data)
  }, [ getCoursesQuery.isSuccess, getCoursesQuery.data?.success ])

  const openCourseModal = useCallback((course: Course) => {
    setSelectedCourse(course)
    setEditCourseModalOpen(true)
  }, [ setSelectedCourse, setEditCourseModalOpen ])

  return (
    <Fragment>
      <Card width="100%">
        <CardHeader>
          <Heading size="md">Courses</Heading>
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
                  </Tr>
                </Thead>
                <Tbody>
                  {courses.map(course => (
                    <Tr key={course.id} onClick={() => openCourseModal(course)}>
                      <Td>{course.id}</Td>
                      <Td>{course.name}</Td>
                      <Td>{course.teacherName}</Td>
                    </Tr>
                  ))}
                </Tbody>
              </Table>
            )
          }
        </CardBody>
      </Card>
      {isEditCourseModalOpen && selectedCourse &&
        <EditCourseModal close={() => setEditCourseModalOpen(false)} course={selectedCourse} />
      }
    </Fragment>
  )
}