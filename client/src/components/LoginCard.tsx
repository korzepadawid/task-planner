import React, { useState } from 'react';
import Typography from '@mui/material/Typography';
import { LoadingButton } from '@mui/lab';
import {
  Alert,
  Button,
  Card,
  CardContent,
  Grid,
  TextField,
} from '@mui/material';

const LoginCard: React.FC = () => {
  const [error, setError] = useState(false);
  const [loading, setLoading] = useState(false);

  const handleSubmit: VoidFunction = async () => {
    setLoading(true);
    const data = { email: 'string@email.com', password: 'string' };
    const response = await fetch('http://localhost:8080/api/v1/auth/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      },
      body: JSON.stringify(data),
    });

    if (response.status === 400 || response.status === 403) {
      setError(true);
    } else {
      setError(false);
      const { accessToken } = await response.json();
      console.log(accessToken);
    }

    setLoading(false);
  };

  return (
    <Card>
      <CardContent>
        <Grid container spacing={3} direction="column" alignItems="center">
          <Grid item xs={12}>
            <Typography variant="h6" component="h3" gutterBottom>
              Login
            </Typography>
          </Grid>
          {error && (
            <Grid item xs={12}>
              <Alert severity="error">
                Ooops, invalid credentials. Try again, please!
              </Alert>
            </Grid>
          )}
          <Grid item xs={12}>
            <TextField
              fullWidth
              id="outlined-basic"
              label="Email"
              variant="outlined"
              size="small"
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              id="outlined-basic"
              label="Password"
              type="password"
              variant="outlined"
              size="small"
            />
          </Grid>
          <Grid
            item
            container
            spacing={3}
            direction="row"
            justifyContent="center"
            alignItems="center"
          >
            <Grid item>
              <LoadingButton
                variant="contained"
                onClick={handleSubmit}
                loading={loading}
              >
                Login
              </LoadingButton>
            </Grid>
            <Grid item>
              <Button href="#text-buttons">Register</Button>
            </Grid>
          </Grid>
        </Grid>
      </CardContent>
    </Card>
  );
};

export default LoginCard;
