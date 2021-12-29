import React from 'react';
import Typography from '@mui/material/Typography';
import { Button, Card, CardContent, Grid, TextField } from '@mui/material';

const LoginCard: React.FC = () => (
  <Card>
    <CardContent>
      <Grid container spacing={3} direction="column" alignItems="center">
        <Grid item xs={12}>
          <Typography variant="h6" component="h3" gutterBottom>
            Login
          </Typography>
        </Grid>
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
            <Button variant="contained">Login</Button>
          </Grid>
          <Grid item>
            <Button href="#text-buttons">Register</Button>
          </Grid>
        </Grid>
      </Grid>
    </CardContent>
  </Card>
);

export default LoginCard;
