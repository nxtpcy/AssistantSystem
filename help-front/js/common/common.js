 /*序列化表单*/
 $.fn.serializeObject = function() {
     var o = {};
     var a = this.serializeArray();
     $.each(a, function() {
         if (o[this.name] !== undefined) {
             if (!o[this.name].push) {
                 o[this.name] = [o[this.name]];
             }
             o[this.name].push(this.value || '');
         } else {
             o[this.name] = this.value || '';
         }
     });
     return o;
 };

 //扩展Date的format方法   
 Date.prototype.format = function(format) {
         var o = {
             "M+": this.getMonth() + 1,
             "d+": this.getDate(),
             "h+": this.getHours(),
             "m+": this.getMinutes(),
             "s+": this.getSeconds(),
             "q+": Math.floor((this.getMonth() + 3) / 3),
             "S": this.getMilliseconds()
         }
         if (/(y+)/.test(format)) {
             format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
         }
         for (var k in o) {
             if (new RegExp("(" + k + ")").test(format)) {
                 format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
             }
         }
         return format;
     }
     /**   
      *转换日期对象为日期字符串   
      * @param date 日期对象   
      * @param isFull 是否为完整的日期数据,   
      *               为true时, 格式如"2000-03-05 01:05:04"   
      *               为false时, 格式如 "2000-03-05"   
      * @return 符合要求的日期字符串   
      */
 function getSmpFormatDate(date, isFull) {
     var pattern = "";
     if (isFull == true || isFull == undefined) {
         pattern = "yyyy-MM-dd hh:mm:ss";
     } else {
         pattern = "yyyy-MM-dd";
     }
     return getFormatDate(date, pattern);
 }
 /**   
  *转换当前日期对象为日期字符串   
  * @param date 日期对象   
  * @param isFull 是否为完整的日期数据,   
  *               为true时, 格式如"2000-03-05 01:05:04"   
  *               为false时, 格式如 "2000-03-05"   
  * @return 符合要求的日期字符串   
  */

 function getSmpFormatNowDate(isFull) {
     return getSmpFormatDate(new Date(), isFull);
 }
 /**   
  *转换long值为日期字符串   
  * @param l long值   
  * @param isFull 是否为完整的日期数据,   
  *               为true时, 格式如"2000-03-05 01:05:04"   
  *               为false时, 格式如 "2000-03-05"   
  * @return 符合要求的日期字符串   
  */

 function getSmpFormatDateByLong(l, isFull) {
     if (l) {
         return getSmpFormatDate(new Date(l), isFull);
     } else {
         return "";
     }
 }
 /**   
  *转换long值为日期字符串   
  * @param l long值   
  * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss   
  * @return 符合要求的日期字符串   
  */

 function getFormatDateByLong(l, pattern) {
     return getFormatDate(new Date(l), pattern);
 }
 /**   
  *转换日期对象为日期字符串   
  * @param l long值   
  * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss   
  * @return 符合要求的日期字符串   
  */
 function getFormatDate(date, pattern) {
     if (date == undefined) {
         date = new Date();
     }
     if (pattern == undefined) {
         pattern = "yyyy-MM-dd hh:mm:ss";
     }
     return date.format(pattern);
 }
 /*将date型转换成long*/
 function dateToLong(str, begin) {
     var moveInArray = str.split('-');
     console.log(moveInArray);
     var d = new Date();
     d.setYear(moveInArray[0]);
     d.setMonth(moveInArray[1] - 1);
     d.setDate(moveInArray[2]);


     var moveIn = d.getTime();
     return moveIn
         // console.log(moveIn);
 }

 function dateToLong2(str) {
     var moveInArray = str.split('-');
     console.log(moveInArray);
     var year = moveInArray[0];
     var month = moveInArray[1];
     var day = moveInArray[2];

     var newstr = year + '/' + month + '/' + day;
     console.log(newstr);
     var d = new Date(newstr)
     var moveIn = d.getTime();
     console.log(moveIn)
     console.log(d);
     return moveIn;
 }
 //alert(getSmpFormatDate(new Date(1279849429000), true));  
 //alert(getSmpFormatDate(new Date(1279849429000),false));      
 //alert(getSmpFormatDateByLong(1279829423000, true));  
 // alert(getSmpFormatDateByLong(1279829423000, false));
 //alert(getFormatDateByLong(1279829423000, "yyyy-MM"));  
 //alert(getFormatDate(new Date(1279829423000), "yy-MM"));  
 //alert(getFormatDateByLong(1279849429000, "yyyy-MM hh:mm"));
