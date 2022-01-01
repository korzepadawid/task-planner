import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

interface TaskList {
  id: number;
  title: string;
  createdAt: number;
  undone: number;
  done: number;
  total: number;
}

interface Props {
  taskLists: TaskList[];
}

const TaskListsTable: React.FC<Props> = ({ taskLists }) => {
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>Task list</TableCell>
            <TableCell align="right">Undone</TableCell>
            <TableCell align="right">Done</TableCell>
            <TableCell align="right">total</TableCell>
            <TableCell align="right">Created at</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {taskLists.map((taskList) => (
            <TableRow
              key={taskList.id}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
              <TableCell>{taskList.title}</TableCell>
              <TableCell align="right">{taskList.undone}</TableCell>
              <TableCell align="right">{taskList.done}</TableCell>
              <TableCell align="right">{taskList.total}</TableCell>
              <TableCell align="right">{taskList.createdAt}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default TaskListsTable;
