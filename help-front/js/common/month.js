  function myFormatter(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    return y + '' + (m < 10 ? ('0' + m) : m);
  }
  //返回格式为1979-1-10
  function myformatter2(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    console.log(d);
    return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
  }

  function myParser(s) {
    if (!s) {
      return new Date();
    }
    var ss = s.split('-');
    var y = parseInt(ss[0], 10);
    var m = parseInt(ss[1], 10);
    if (!isNaN(y) && !isNaN(m)) {
      return new Date(y, m - 1);
    }
  }