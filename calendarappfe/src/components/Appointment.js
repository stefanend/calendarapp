import { Card, CardContent, Typography, TextField, Stack, Box } from "@mui/material";
import { useState } from 'react';
import './styles/Appointment.css';


const Appointment = ({appointment, openAlert}) => {
    const [candidate, setCandidate] = useState({
        firstName: '',
        lastName: ''
      });

      const [interviewer, setInterviewer] = useState({
        firstName: '',
        lastName: '',
        experienced: null
      });

    const onEnterKeyCandidate = (keyPress) => {
        if(keyPress.keyCode === 13) {
            const words = keyPress.target.value.split(' ');
            candidate.firstName = words[0];
            candidate.lastName = words[1] || '';
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
                openAlert('Successfully updated candidate information!', 'success');
            })
            .catch((error) => {
                console.log(error.message)
            })
        }
    }

    const onEnterKeyInterviewer = (keyPress, experienced) => {
        if(keyPress.keyCode === 13) {
            const words = keyPress.target.value.split(' ');
            interviewer.firstName = words[0];
            interviewer.lastName = words[1] || '';
            interviewer.experienced = experienced;
            fetch(`http://localhost:8080/api/appointments/interviewer/${appointment.id}`, { 
                method: 'POST',
                headers: {
                    'Accept': 'application/json, text/plain',
                    'Content-Type':'application/json'},
                body: JSON.stringify(interviewer)
            })
            .then(response => {
                return response.json();
            })
            .then((data) => {
                console.log(data);
                openAlert('Successfully updated interviewer information!', 'success');
            })
            .catch((error) => {
                console.log(error.message)
            })
        }
    } 

    const getCandidateName = () => {
        const candidate = appointment.candidate;
        return (candidate && candidate.firstName.length > 0)
            ? candidate.firstName + ' ' + candidate.lastName
            : '';
    }; 

    const getInterviewerName = (isExperienced) => {
        if(!appointment.interviewers || appointment.interviewers.length === 0)
            return '';
        const interviewer = appointment.interviewers.filter(i => i.experienced === isExperienced)[0];

        return interviewer
            ? interviewer.firstName + ' ' + interviewer.lastName
            : '';
    };

    return (
        <div>
            <Card sx={{ width: '100%', height:'15%' }}>
                <CardContent sx={{ padding: '8px 8px', '&:last-child': {paddingBottom: '10px'} }} >
                    <Stack>
                    <Typography variant="h5" sx={{ mb: 0, fontStyle: 'italic', fontSize: '11pt', border:'none', mt: '-18px'}} component="div">
                        <TextField id="outlined-search" placeholder="Candidate Name" type="search" sx={{"& fieldset": { border: 'none' }}} 
                        inputProps={{className: 'input-candidate', style: { padding: '16px 14px 8px 14px' }}} onKeyDown={(e) => onEnterKeyCandidate(e)}
                        defaultValue={getCandidateName()} />
                    </Typography>
                    <Stack sx={{textAlign: "left"}}>
                    <Typography sx={{ mt: 0, fontSize: '9pt' }} color="text.secondary" component='div'>
                        <Box display={'flex'} justifyContent={"space-between"}>
                        <label display="inline-block" className='lbl-interviewer'>Experienced interviewer: </label>
                        <input defaultValue={getInterviewerName(true)} type="text" className='input-interviewer' onKeyDown={(e) => onEnterKeyInterviewer(e, true)} />
                        </Box>
                    </Typography>
                    <Typography mt={'5px'} color="text.secondary" sx={{ fontSize: '9pt'}} component='div'>
                        <Box display={'flex'} justifyContent={"space-between"}>
                        <label display="inline-block" className='lbl-interviewer'>Inexperienced interviewer: </label>
                        <input defaultValue={getInterviewerName(false)} type="text" className='input-interviewer' onKeyDown={(e) => onEnterKeyInterviewer(e, false)} />
                        </Box>
                    </Typography>
                    </Stack>
                    </Stack>
                </CardContent>
            </Card>
        </div>
    );
};

export default Appointment;