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
  IconButton,
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import { Link } from 'react-router-dom';
import { fromUnixToTodayDistance } from '../util/time';

export interface TaskList {
  id: number;
  title: string;
  createdAt: number;
  undone: number;
  done: number;
  total: number;
}

interface Props {
  taskLists: TaskList[];
  deleteTaskListById: (id: number) => Promise<void>;
}

const TaskListsTable: React.FC<Props> = ({ taskLists, deleteTaskListById }) => {
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>Task list</TableCell>
            <TableCell align="center">Undone</TableCell>
            <TableCell align="center">Done</TableCell>
            <TableCell align="center">total</TableCell>
            <TableCell align="center">Created at</TableCell>
            <TableCell align="center">Remove</TableCell>
            <TableCell align="center">Edit</TableCell>
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
                  <Link to={`/task-lists/${taskList.id}?page=1`}>
                    {taskList.title}
                  </Link>
                </Button>
              </TableCell>
              <TableCell align="center">{taskList.undone}</TableCell>
              <TableCell align="center">{taskList.done}</TableCell>
              <TableCell align="center">{taskList.total}</TableCell>
              <TableCell align="center">
                {fromUnixToTodayDistance(taskList.createdAt)}
              </TableCell>
              <TableCell align="center">
                <IconButton
                  aria-label="delete"
                  color="primary"
                  onClick={() => deleteTaskListById(taskList.id)}
                >
                  <DeleteIcon />
                </IconButton>
              </TableCell>
              <TableCell align="center">
                <IconButton aria-label="delete" color="primary">
                  <EditIcon />
                </IconButton>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default TaskListsTable;
