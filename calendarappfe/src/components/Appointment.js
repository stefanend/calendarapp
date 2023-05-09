import { Box, Card, CardContent, Typography } from "@mui/material";

const Appointment = ({appointment}) => {
    return (
        <div>
            <Card>
                <CardContent>
                    <Typography variant="h5" sx={{ mb: 0.5, fontStyle: 'italic', fontSize: '14pt' }} component="div">
                        { appointment.candidate.firstName } { appointment.candidate.lastName }
                    </Typography>
                    <Box sx={{textAlign: "left"}}>
                    <Typography sx={{ mb: 0.2, fontSize: '11pt' }} color="text.secondary">
                        Experienced interviewer: 
                        <Box display={'inline'} sx={{color: "black", ml: 0.3}}>
                            { appointment.interviewers.filter(i => i.isExperienced)[0].firstName } 
                        </Box>
                    </Typography>
                    <Typography color="text.secondary" sx={{ fontSize: '11pt'}}>
                        Inexperienced interviewer: 
                        <Box display={'inline'} sx={{color: "black", ml: 0.3}}>
                            { appointment.interviewers.filter(i => !i.isExperienced)[0].firstName }
                        </Box>
                    </Typography>
                    </Box>
                </CardContent>
            </Card>
        </div>
    );
};

export default Appointment;