import React, { useEffect } from 'react';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { Button, Grid, TextField, Typography } from '@mui/material';
import axios from 'axios';
import { useParams, useHistory } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import { useSelector } from 'react-redux';
import { MainState } from '../store';

interface Params {
  id: string;
}

interface FormInput {
  title: string;
}

const formSchema = yup
  .object({
    title: yup.string().trim().required(),
  })
  .required();

const EditTaskListPage: React.FC = () => {
  const { id } = useParams<Params>();
  const history = useHistory();
  const {
    register,
    setValue,
    handleSubmit,
    formState: { errors },
  } = useForm<FormInput>({
    resolver: yupResolver(formSchema),
  });
  const { accessToken } = useSelector((state: MainState) => state);

  const submitForm = async (input: FormInput): Promise<void> => {
    try {
      await axios.patch(
        `http://localhost:8080/api/v1/task-lists/${id}`,
        {
          ...input,
        },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      history.push(`/task-lists`);
    } catch {
      console.error('error');
    }
  };

  useEffect(() => {
    const fetchTaskList = async (): Promise<void> => {
      try {
        const { data } = await axios.get(
          `http://localhost:8080/api/v1/task-lists/${id}`,
          {
            headers: {
              Authorization: `Bearer ${accessToken}`,
            },
          }
        );
        const { title } = data;
        setValue('title', title, { shouldDirty: true });
      } catch {
        console.error('error');
      }
    };
    fetchTaskList();
  }, []);
  return (
    <>
      <Typography variant="h6" component="h3" gutterBottom>
        Edit your task list
      </Typography>
      <form onSubmit={handleSubmit(submitForm)}>
        <Grid direction="column" container spacing={3}>
          <Grid item xs={12}>
            <TextField
              hiddenLabel
              {...register('title')}
              id="outlined-basic"
              variant="outlined"
            />
          </Grid>
          <Grid item xs={12}>
            <Button type="submit" variant="contained">
              Submit
            </Button>
          </Grid>
        </Grid>
      </form>
    </>
  );
};

export default EditTaskListPage;
