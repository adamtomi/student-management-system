import { useCallback, useEffect, useState } from 'react'
import { Box, Input, Button, Spinner, Table, Thead, Tbody, Tr, Th, Td } from '@chakra-ui/react'
import { useQuery } from '@tanstack/react-query'
import { findStudent, getStudents } from '../api/student.ts'
import type { Student } from '../types'

export function StudentsTable() {
  const [ students, setStudents ] = useState<Student[]>([])
  const [ studentId, setStudentId ] = useState<string>('')
  const [ selectedStudent, setSelectedStudent ] = useState<Student | undefined>()

  const getStudentsQuery = useQuery({
    queryKey: [ 'students-get' ],
    queryFn: () => getStudents()
  })

  const findStudentQuery = useQuery({
    queryKey: [ 'student-find' ],
    queryFn: () => findStudent(studentId),
    enabled: false
  })

  useEffect(() => {
    if (getStudentsQuery.isSuccess && getStudentsQuery.data.success) {
      setStudents(getStudentsQuery.data.data)
    }
  }, [ getStudentsQuery.isSuccess, getStudentsQuery.data?.success ])

  useEffect(() => {
    if (findStudentQuery.isSuccess && findStudentQuery.data.success) {
      setSelectedStudent(findStudentQuery.data.data)
    }
  }, [ findStudentQuery.isSuccess, findStudentQuery.data?.success ])

  const studentName = useCallback((student: Student) => `${student.firstName} ${student.lastName}`, [])

  return (
    <Box>
      <Input
        placeholder="Enter Student ID"
        value={studentId}
        onChange={(e) => setStudentId(e.target.value)}
      />
      <Button onClick={() => findStudentQuery.refetch()}>Find Student</Button>
      {selectedStudent && (
        <Box>
          <p>Student ID: {selectedStudent.id}</p>
          <p>Name: {studentName(selectedStudent)}</p>
          {/* Display more student details as needed */}
        </Box>
      )}
      {getStudentsQuery.isFetching
        ? <Spinner />
        : (
          <Table>
            <Thead>
              <Tr>
                <Th>ID</Th>
                <Th>Name</Th>
                {/* Add more columns as needed */}
              </Tr>
            </Thead>
            <Tbody>
              {students.map(student => (
                <Tr key={student.id}>
                  <Td>{student.id}</Td>
                  <Td>{studentName(student)}</Td>
                  {/* Add more cells as needed */}
                </Tr>
              ))}
            </Tbody>
          </Table>
        )
      }
    </Box>
  );
};
