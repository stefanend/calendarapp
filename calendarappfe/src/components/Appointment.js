import { Card, CardContent, Typography, TextField, Stack } from "@mui/material";
import { useState } from 'react';


const Appointment = ({appointment}) => {
    const [candidate, setCandidate] = useState({
        firstName: '',
        lastName: ''
      });

    const onEnterKey = (keyPress) => {
        if(keyPress.keyCode === 13) {
            const words = keyPress.target.value.split(' ');
            candidate.firstName = words[0];
            candidate.lastName = words[1];
            fetch(`http://localhost:8080/api/appointments/candidate/${appointment.id}`, { 
                method: 'POST',
                headers: {
                    'Accept': 'application/json, text/plain',
                    'Content-Type':'application/json'},
                body: JSON.stringify(candidate)
            })
            .then(response => {
                return response.json();
            })
            .then((data) => {
                console.log(data);
            })
            .catch((error) => {
                console.log(error.message)
            })
        }
    }
    return (
        <div>
            <Card sx={{ width: '100%', height:'15%' }}>
                <CardContent sx={{ padding: '8px 16px', '&:last-child': {paddingBottom: '10px'} }} >
                    <Stack>
                    <Typography variant="h5" sx={{ mb: 0, float: 'left', fontStyle: 'italic', fontSize: '14pt', border:'none'}} component="div">
                        <TextField id="outlined-search" label="Candidate Name" type="search" sx={{"& fieldset": { border: 'none' }}} 
                        InputLabelProps={{ style: {fontSize: '11pt'} }} inputProps={{style: {paddingBottom: '8px'}}} onKeyDown={(e) => onEnterKey(e)}/>
                    </Typography>
                    <Stack sx={{textAlign: "left"}}>
                    <Typography sx={{ mt: 0, fontSize: '11pt' }} color="text.secondary">
                        <label display="inline-block">Experienced interviewer: </label>
                        <input type="text" style={{border:"none"}}/>
                    </Typography>
                    <Typography color="text.secondary" sx={{ fontSize: '11pt'}}>
                        <label display="inline-block">Inexperienced interviewer: </label>
                        <input type="text" style={{border:"none"}} />
                    </Typography>
                    </Stack>
                    </Stack>
                </CardContent>
            </Card>
        </div>
    );
};

export default Appointment;