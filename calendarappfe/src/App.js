import { useState } from 'react';
import './App.css';
import ResponsiveGrid from './components/Grid';
import SnackbarAlert from './components/SnackbarAlert';
import { Container } from '@mui/material';

function App() {
  const [alertOpen, setAlertOpen] = useState(false);
  const [alertMessage, setAlertMessage] = useState('');
  const [alertSeverity, setAlertSeverity] = useState('info');

  const openAlert = (message, severity) => {
    setAlertOpen(true);
    setAlertMessage(message);
    setAlertSeverity(severity);
  };

  const handleAlertClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }

    setAlertOpen(false);
  };

  return (
    <Container maxWidth={false}>
      <ResponsiveGrid openAlert={openAlert} />
      <SnackbarAlert
        open={alertOpen}
        handleClose={handleAlertClose}
        message={alertMessage}
        severity={alertSeverity}
      />
    </Container>
  );
}

export default App;
