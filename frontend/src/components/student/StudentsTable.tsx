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
import { getStudents } from '../../api/student'
import { EditStudentModal } from './EditStudentModal'
import type { Student} from '../../types'

export function StudentsTable() {
  const [ students, setStudents ] = useState<Student[]>([])
  const [ selectedStudent, setSelectedStudent ] = useState<Student | undefined>()
  const [ isEditStudentModalOpen, setEditStudentModalOpen ] = useState<boolean>(false)

  const getStudentsQuery = useQuery({
    queryKey: [ 'students-get' ],
    queryFn: () => getStudents()
  })

  useEffect(() => {
    if (getStudentsQuery.isSuccess && getStudentsQuery.data.success) setStudents(getStudentsQuery.data.data)
  }, [ getStudentsQuery.isSuccess, getStudentsQuery.data?.success ])

  const studentName = useCallback((student: Student) => `${student.firstName} ${student.lastName}`, [])

  const openStudentModal = useCallback((student: Student) => {
    setSelectedStudent(student)
    setEditStudentModalOpen(true)
  }, [ setSelectedStudent, setEditStudentModalOpen ])

  return (
    <Fragment>
      <Card width="100%">
        <CardHeader>
          <Heading size="md">Students</Heading>
        </CardHeader>
        <CardBody>
          {getStudentsQuery.isFetching
            ? <Spinner />
            : (
              <Table>
                <Thead>
                  <Tr>
                    <Th>ID</Th>
                    <Th>Name</Th>
                    <Th>Email</Th>
                  </Tr>
                </Thead>
                <Tbody>
                  {students.map(student => (
                    <Tr key={student.id} onClick={() => openStudentModal(student)}>
                      <Td>{student.id}</Td>
                      <Td>{studentName(student)}</Td>
                      <Td>{student.email}</Td>
                    </Tr>
                  ))}
                </Tbody>
              </Table>
            )
          }
        </CardBody>
      </Card>

      {isEditStudentModalOpen && selectedStudent &&
        <EditStudentModal close={() => setEditStudentModalOpen(false)} student={selectedStudent} />
      }
    </Fragment>
  )
}
