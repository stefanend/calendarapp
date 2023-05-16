import { Card, CardContent, Typography, TextField, Stack, Box } from "@mui/material";
import './styles/Appointment.css';

const Appointment = ({appointment}) => {
    return (
        <div>
            <Card sx={{ width: '100%', height:'15%' }}>
                <CardContent sx={{ padding: '8px 8px', '&:last-child': {paddingBottom: '10px'} }} >
                    <Stack>
                    <Typography variant="h5" sx={{ mb: 0, fontStyle: 'italic', fontSize: '11pt', border:'none', mt: '-18px'}} component="div">
                        <TextField id="outlined-search" placeholder="Candidate Name" type="search" sx={{"& fieldset": { border: 'none' }, '& placeholder': { fontSize: '3pt' }}} 
                        inputProps={{className: 'input-candidate', style: { padding: '16px 14px 8px 14px' }}}/>
                    </Typography>
                    <Stack sx={{textAlign: "left"}}>
                    <Typography sx={{ mt: 0, fontSize: '9pt' }} color="text.secondary">
                        <Box display={'flex'} justifyContent={"space-between"}>
                        <label display="inline-block" className='lbl-interviewer'>Experienced interviewer: </label>
                        <input type="text" className='input-interviewer' />
                        </Box>
                    </Typography>
                    <Typography mt={'5px'} color="text.secondary" sx={{ fontSize: '9pt'}}>
                        <Box display={'flex'} justifyContent={"space-between"}>
                        <label display="inline-block" className='lbl-interviewer'>Inexperienced interviewer: </label>
                        <input type="text" className='input-interviewer' />
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