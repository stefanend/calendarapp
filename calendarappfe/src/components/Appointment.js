import { Box, Card, CardContent, Typography, TextField, InputLabel } from "@mui/material";

const Appointment = ({appointment}) => {
    return (
        <div>
            <Card sx={{ width: '30%', height:'15%' }}>
                <CardContent>
                    <Typography variant="h5" sx={{ mb: 0, mr: 10, float: 'left', fontStyle: 'italic', fontSize: '14pt', border:'none'}} component="div">
                        <TextField id="outlined-search" label="Candidate Name" type="search" sx={{"& fieldset": { border: 'none' }}}/>
                    </Typography>
                    <Box sx={{textAlign: "left"}}>
                    <Typography sx={{ mt: 0.5, fontSize: '12pt' }} color="text.secondary">
                        <label display="inline-block">Experienced interviewer: </label>
                        <input type="text" style={{border:"none"}}/>
                    </Typography>
                    <Typography color="text.secondary" sx={{ fontSize: '12pt'}}>
                        <label display="inline-block">Inexperienced interviewer: </label>
                        <input type="text" style={{border:"none"}} />
                    </Typography>
                    </Box>
                </CardContent>
            </Card>
        </div>
    );
};

export default Appointment;