import Snackbar from "@mui/material/Snackbar";
import { Alert } from "@mui/material";

export default function SnackbarAlert({ open, handleClose, message, severity }) {

    
      return (
          <Snackbar 
            open={open} 
            autoHideDuration={6000} 
            onClose={handleClose} 
            anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
            sx={{ position: 'absolute' }}
        >
            <Alert onClose={handleClose} severity={severity} sx={{ width: '100%' }} variant='filled' elevation={6}>
              {message}
            </Alert>
          </Snackbar>
        );
}