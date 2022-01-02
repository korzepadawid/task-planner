import React from 'react';
import {
  Table,
  TableContainer,
  TableHead,
  Paper,
  TableRow,
  TableCell,
  TableBody,
  Button,
} from '@mui/material';
import { fromUnixToTodayDistance } from '../util/time';

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
              <TableCell>
                <Button size="small" href="#text-buttons">
                  {taskList.title}
                </Button>
              </TableCell>
              <TableCell align="right">{taskList.undone}</TableCell>
              <TableCell align="right">{taskList.done}</TableCell>
              <TableCell align="right">{taskList.total}</TableCell>
              <TableCell align="right">
                {fromUnixToTodayDistance(taskList.createdAt)}
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default TaskListsTable;
