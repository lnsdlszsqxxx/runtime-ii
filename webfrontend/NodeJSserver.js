var http = require('http');
var fs = require('fs');
console.log('Server running on 3000...');

http.createServer(function (req, res) {
  if(req.url=='/getDepartment'){
    fs.readFile('getDepartment.html',null,function(error, data) {
      res.writeHead(200, {'Content-Type': 'text/html'});
      res.write(data);
      res.end();
    });
  }
  else{
    fs.readFile('auth.html',null,function(error, data) {
      res.writeHead(200, {'Content-Type': 'text/html'});
      if (error) {
          res.writeHead(404);
          res.write('Whoops! File not found!');
      } else {
          res.write(data);
      }
      res.end();
    });
  }
}).listen(3000);
