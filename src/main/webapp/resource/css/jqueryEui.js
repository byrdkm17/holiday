/**
*jquery ui plugin
*by awen
*email awen1983@live.cn
*/
(function($){
	/**浏览器常量*/
	var isIECore=!-[1,],
		isWebkitCore=(/webkit/i).test(navigator.userAgent),
		is360=(/360SE/i).test(navigator.userAgent),
		isSogou=(/MetaSr/i).test(navigator.userAgent),
		isQQ=navigator.userAgent.indexOf("QQBrowser")>-1,		/**邪门这里用正则无效*/
		/**遨游判断这里有点郁闷呢，否则，《有些~》ie8 window.external.max_version 这句话直接报错！而不是undefined，难道跟跟更新包有关系？*/
		isMaxthon=(function(){
			if(window.external){
				try{
					if(window.external.max_version){
						return window.external.max_version.substr(0,1)>0;
					}
				}catch(e){}
				return false;
			}
			return false;
 		})(),
		isMaxthonHightModen=isMaxthon&&((/Maxthon/i).test(navigator.appVersion)),
		isOpera=window.opera?true:false,
		isIE=isIECore&&!isMaxthon&&!isSogou&&!isQQ,/**排除搜狗和遨游浏览器，360就不用排除了基本根ie一样，所以就当ie了*/
		isIE6=isIE&&(/MSIE 6.0/i).test(navigator.appVersion),
		isIE7=isIE&&(/MSIE 7.0/i).test(navigator.appVersion),
		isFirefox=(/Firefox/i).test(navigator.userAgent),
		isSafari=(/Safari/.test(navigator.userAgent))&&(!(/Chrome/.test(navigator.userAgent))),
		isChrome=( /Chrome/i).test(navigator.userAgent)&&!isSogou&&!isMaxthon&&!isQQ;

 	/**获取url的diomain主域*/
	function getUrlMainDomain(url){ 
		 if(/localhost/i.test(url)){
		    return "localhost";
		 }
		 var patt =/\w+[\.]?(com|net|org|gov|cc|biz|info|cn|tk)(\.(cn|hk))?/i;
		 var arr = url.match(patt);
		 if(arr.length > 0) return arr[0];
		 else return false;
	} 	 
	/**
	 *在全局中增加设置标记信息
	 *@param hashkey 要增加唯一标志，
	 *@return  如果以前曾经设置过该标志，false else true
	 */
	function addConfig(hashkey){
		if(!window.cczwConfig){
			window.cczwConfig={};
		}
		if(!cczwConfig[hashkey]){
			cczwConfig[hashkey]=true;
			return true;
		}
		return false;
	}
	/**
	 *在web的style标签中增加css样式
	 *@param hashkey 要增加样式的唯一标志，如果以前曾经增加过该标志的样式，则本函数将不再执行
	 *@param csstext 正常的css样式字符串
	 */
	function addConfigStyle(hashkey,csstext){
		if(addConfig(hashkey)){
			//查看本程序的style块是否存在
			var styleNode=document.querySelector("style[title=cczw]");
			if(!styleNode){
				styleNode=document.createElement("style");
				styleNode.title="cczw";
				document.querySelector("head").appendChild(styleNode);
			}
			if(csstext){
				var csst=document.createTextNode(csstext);
				styleNode.appendChild(csst);
			}
		}
	}
	function queryToJson(strQuery,checkValueType){
		var json={};
		var objs=strQuery.split("&");
		for(var i=0,len=objs.length;i<len;i++){
			if(objs[i].indexOf("=")>-1){
				var obj=objs[i].split("=");
				//判断空
				if(!((/^\s*$/ig).test(obj[0]))){
					var val=$.trim(obj[1]);
					if(val=="null"||val=="undefined"){val="";}
					if(checkValueType&&(/^\d+$/ig).test(val)){
	                    json[obj[0]]=parseInt(val);
					}else{
						json[obj[0]]=val;
					}
					
				}
			}
		}
		return json;
	}
	
	function buildMultiSelect(setting){
		if(!setting){alert('参数错误');}
		var domdataname="seldata";
		var key_value=setting.keyValue;
		var key_text=setting.keyText;
		var m_jsonData=setting.jsonData;
		this.selskeys=setting.selskeys;
		this.selsids=setting.selsids;
		this.showValues=setting.showValues||Array(3);
		this.sellen=this.selsids.length;
		var instance=this;

 		var jsonobj=m_jsonData;
		var curidx=0;
		for(var i=0;i<instance.sellen;i++){
			var selobj=$("#"+instance.selsids[i]);
			
			if(i==0){
				jsonobj=jsonobj[instance.selskeys[i]];
			}else{
				if(!jsonobj[curidx]){
						break;
				}
 				jsonobj=jsonobj[curidx][instance.selskeys[i]];
			}
			//定位对应的json数据
			if(!jsonobj){break;}
			for(var j=0,l=jsonobj.length;j<l;j++){
				try{
					if(jsonobj[j][key_value]==instance.showValues[i]||0){
							curidx=j;
							selobj.append("<option value='"+jsonobj[j][key_value]+"' selected>"+jsonobj[j][key_text]+"</option>");
					}else{
							selobj.append("<option value='"+jsonobj[j][key_value]+"'>"+jsonobj[j][key_text]+"</option>");
					}
				}catch(e){}
			}
			selobj.data(domdataname,{json:jsonobj,selidx:i});
			selobj.bind("change",function(){flushsel($(this).data(domdataname),$(this).get(0).selectedIndex);});
			selobj.css("zoom",1);
		}
 
		/**刷新某select
		 *selidx  select的索引及第几个select 从0开始
		 * */
		function flushsel(seljsondata,selectedidx){
			var jsonobj=seljsondata['json'][selectedidx];
			var selidx=seljsondata['selidx'];
			for(var i=selidx+1;i<instance.sellen;i++){
					var selobj=$("#"+instance.selsids[i]);
					selobj.empty();
					try{
						if( i==selidx+1){
							jsonobj=jsonobj[instance.selskeys[i]];
						}else{
							if(!jsonobj||jsonobj.length==0){
								break;
							}
							jsonobj=jsonobj[0][instance.selskeys[i]];
						}
						//定位对应的json数据
						if(!jsonobj){break;}
						for(var j=0,l=jsonobj.length;j<l;j++){
							selobj.append("<option value='"+jsonobj[j][key_value]+"'>"+jsonobj[j][key_text]+"</option>");
						}
					}catch(e){}
					selobj.data(domdataname,{json:jsonobj,selidx:i});
 			}
 			if(isIE){
				$("#"+instance.selsids[0]).parent().css("zoom","normal");
				$("#"+instance.selsids[0]).parent().css("zoom",1);
			}

		}
	}
	
	function tabs(setting){
 		 var tabs=$(setting.selector).children("[data-for]");
		 var curshowidx=setting.showidx||0;
		 var onClass=setting.onClass;
		 var offClass=setting.offClass;
		 var waiting=setting.loading?setting.loading:false;
		 var funtype=setting.type?setting.type:"mouseover";
		 var rollback=setting.rollback?setting.rollback:false;
		 //如果curshowidx为-1 ,则搜索当前显示id
  		 if(curshowidx==-1){
  		 	for (var i = tabs.length - 1; i >= 0; i--) {
  		 		if(tabs[i].className==onClass){
  		 			curshowidx=i;
  		 		}
  		 	};
  		 }
  		 
  		 var showids=[];
   		 for(var i=0;i<tabs.length;i++){
 			tabs[i]=$(tabs[i]);
			var datafor=tabs[i].attr("data-for");
 			showids[i]=datafor?datafor:false;
 			tabs[i].bind(funtype,showids,function(o){
				 var curforid=$(this).attr("data-for");
				 changeShowTab(o.data,curforid);
			});
  		 }

		 //显示默认tab	
 		changeShowTab(showids,showids[curshowidx]);
 			
 		//是否回滚
 		if(rollback){
 			//监控鼠标状态
 			$(setting.selector).parent().bind("mouseout",
 				function(event){
 					var box=$(this).getBox();
 					if(!(box.left<event.clientX&&event.clientX<box.right)||!(box.top<event.clientY&&event.clientY<box.bottom)){
 						setTimeout(function(){changeShowTab(showids,showids[curshowidx])},500);
 					}
	 			}
 			);
 		}
		//tab切换
		function changeShowTab(ids,curid){
			//tabs
			tabs.each(function(i){
				var showid=$(this).attr("data-for");
				if(showid==curid){
					 if(offClass){$(this).removeClass(offClass);}
					 if(onClass){$(this).addClass(onClass);}
				}else{
					 if(onClass){$(this).removeClass(onClass);}
					 if(offClass){$(this).addClass(offClass);}
				}
 			});
			//shows
  			for(var i=0,len=ids.length;i<len;i++){
				 if(curid==ids[i]){
					 $(ids[i]).css("display","block");
					 //判断是否需要加载数据
					 var dataurl=$(curid).attr("data-url");
 					 if(dataurl){
						 if(waiting){$(curid).html(waiting)};
						 //火狐默认的xhtml的doctype 获取回来的居然是xml，所以定义类型为html
 						 $.get(dataurl,function(data){
  							  $(curid).html(data);
   							  $(curid).removeAttr("data-url");
  						},"html");
					 }
				 }else{
					 $(ids[i]).css("display","none");
				 }
			 }
		}
	}
		
	function slider(setting){
		var container=$(setting.selector);
		var autoPlay=setting.autoPlay==false?false:true;
		var autoPlayTime=setting.autoPlayTime?setting.autoPlayTime:5000;
		var actionEvent=setting.actionEvent==undefined?"click":setting.actionEvent;
  		var hastitle=setting.hasTitle==undefined?true:setting.hasTitle;
  		var animateStyle=setting.animateStyle;
  		var barstyle=setting.barStyle==undefined?"images":setting.barStyle;   					//number,line,images
	  	var barpos=setting.barPosition==undefined?"bottom":setting.barPosition;   				//bar的方位
   		var barsize=setting.barSize==undefined?68:setting.barSize;							   //bar的总大小，当bar为垂直位置时 是高度，水平位置时是宽度

		var curidx=0;
		var mh=container.height();
		var mw=container.width();

 		var titlebarh=setting.titleBarH==undefined?28:setting.titleBarH;								//titlebar的高度
 		var titlebarimg=setting.titleBarBackImg;														//titlebar的背景图片 优先级高于颜色
 		var titlebarcolor=setting.titleBarBackColor==undefined?"#333":setting.titleBarBackColor;		//titlebar的背景颜色
 		var titlebaralpha=setting.titleBarBackAlpha==undefined?0.5:setting.titleBarBackAlpha;			//titlebar的背景颜色透明度
 		var titlefontcolor=setting.titleBarFontColor==undefined?"#fff":setting.titleBarFontColor;		//titlebar的字颜色

 		var listh=(barpos=="bottom"||barpos=="top")?(barsize-titlebarh):mh-titlebarh;		     		//list高度
 		var listalign=setting.barAlign==undefined?"center":setting.barAlign;  					 		//list的对齐方式
 		var listimg=setting.listBackImg;																//list的背景图片 优先级高于颜色
 		var listcolor=setting.listBackColor==undefined?"#333":setting.listBackColor;					//list的背景颜色
 		var listfontcolor=setting.listFontColor==undefined?"#fff":setting.listFontColor;				//list的背景颜色
 		var listbaralpha=setting.listBarBackAlpha==undefined?0.5:setting.listBarBackAlpha;			//list的背景颜色透明度

		var thumbSize=setting.thumbSize==undefined?listh-10:setting.thumbSize;
 
 		var bar_css_on=setting.barCssOn==undefined?{"opacity":1,"border":"2px solid #f00"}:setting.barCssOn;
 		var bar_css_off=setting.barCssOff==undefined?{"opacity":1,"border":"2px solid #fff"}:setting.barCssOff;
		var datas=setting.datas?setting.datas:[];



		
		var shows=[];
		var bars=[];
  		//build
		container.css({});
		var wraper=$("<div id='wraper'>").css({"position":"relative","border":"none","width":mw+"px","height":mh+"px","overflow":"hidden"});
		var scroller=$("<div id='scroller'>").css({"position":"relative","border":"none"});
		if(animateStyle=="linear"){scroller.width(mw*(datas.length+1));}

 		var infobar=$("<div>").css({"position":"absolute","border":"none","margin":"0","padding":"0","zIndex":100,"overflow":"hidden"});
	    var titleback=$("<div>").css({"position":"absolute","left":"0","width":mw+"px","height":titlebarh+"px","zIndex":99});
		if(titlebarimg!=undefined){
			if(isIE6 || is360 || isQQ){
				titleback[0].style.cssText="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src="+titlebarimg+");_background:none;width:"+mw+"px;height:"+titlebarh+"px;zIndex:100;top:"+(mh-barsize)+"px";
			}else{
				titleback.css("background","url("+titlebarimg+")");
			}
		}else{
			titleback.css({"background-color":titlebarcolor,"opacity":titlebaralpha});
		}
		var title=$("<div>").css({"position":"absolute","color":titlefontcolor,"font-size":"14px","font-weight":"blod","float":"left","text-align":"center","left":"0px","padding":"0","zIndex":100}).html(datas[0]?datas[0].title:"");
		var bar=$("<ul>").css({"position":"absolute","left":"0","color":listfontcolor,"top":titlebarh+"px","float":"left","margin":"0","padding":"0","zIndex":100,"text-align":"center"});
		//list的背景
		var barback=$("<div>").css({"position":"absolute","left":"0","top":titlebarh+"px","zIndex":99});
		if(listimg!=undefined){
			if(isIE6 || is360 || isQQ){
				barback[0].style.cssText="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src="+listimg+");_background:none;width:"+mw+"px;height:"+titlebarh+"px;zIndex:100;top:"+(mh-barsize)+"px";
			}else{
				barback.css("background","url("+listimg+")");
			}
		}else{
			barback.css({"background-color":listcolor,"opacity":listbaralpha});
		}
		switch(listalign){
			case "left":
				bar.css("text-align","left");
			break;
			case "center":
				bar.css("text-align","center");
			break;
			case "right":
				bar.css("text-align","right");
			break;
		}
   		switch(barpos){
			case "top":
				infobar.css({"left":"0px","top":"0px","width":mw+"px","height":barsize+"px"});
				title.css({"width":mw+"px","height":titlebarh+"px","line-height":titlebarh+"px"});
				titleback.css({"width":mw+"px","height":titlebarh+"px","line-height":titlebarh+"px"});
				bar.css({"width":mw+"px","height":listh+"px"});
				barback.css({"width":mw+"px","height":listh+"px"});
			break;
			case "bottom":
				infobar.css({"left":"0px","top":(mh-barsize)+"px","width":mw+"px","height":barsize+"px"});
				title.css({"width":mw+"px","height":titlebarh+"px","line-height":titlebarh+"px"});
				titleback.css({"width":mw+"px","height":titlebarh+"px","line-height":titlebarh+"px"});
				bar.css({"width":mw+"px","height":listh+"px"});
				barback.css({"width":mw+"px","height":listh+"px"});
 			break;
			case "left":
				infobar.css({"left":"0px","top":"0px","width":barsize+"px","height":mh+"px"});
				title.css({"width":barsize+"px","height":titlebarh+"px","line-height":"18px"});
				titleback.css({"width":barsize+"px","height":titlebarh+"px","line-height":"18px"});
				bar.css({"width":barsize+"px","height":listh+"px"});
				barback.css({"width":barsize+"px","height":listh+"px"});
			break;
			case "right":
				infobar.css({"right":"0px","top":"0px","width":barsize+"px","height":mh+"px"});
				title.css({"width":barsize+"px","height":titlebarh+"px","line-height":"18px"});
				titleback.css({"width":barsize+"px","height":titlebarh+"px","line-height":"18px"});
				bar.css({"width":barsize+"px","height":listh+"px"});
				barback.css({"width":barsize+"px","height":listh+"px"});
			break;
		}
 		for(var i=0,len=datas.length;i<len;i++){
			//img
 			var a=$("<a>").attr({"href":datas[i].url,"target":"_blank"}).css({"margin":"0","padding":"0","zIndex":10,"border":"none"});
			var img=$("<img>").attr({"src":datas[i].img,"alt":datas[i].title}).css({"float":"left","width":mw+"px","border":"none"}).appendTo(a);
			if(curidx!=i&&animateStyle!="linear"){img.css("display","none");}
 			shows[shows.length]=img;
			a.appendTo(scroller);
			//bar item
			var bari=$("<li>");
			var cssobj={"display":(isIE6||isIE7?"inline":"inline-block"),"zoom":1,"marginLeft":"2px","marginRight":"2px","padding":"0","overflow":"hidden","cursor":"pointer","background-color":"#333","color":"#fff","text-align":"center"};
			var margintop=0;
			switch(barstyle){
				case "line":
					marginTop=(listh-5)/2;
					$.extend(cssobj,{"width":"20px","height":"5px","background-color":"#ccc"});
					break;
				case "number":
					marginTop=(listh-20)/2;
					$.extend(cssobj,{"width":"20px","height":"20px","font-size":"12px","line-height":"20px"});
			    	bari.html(i+1);
				break;	
				case "images":
				default:
					marginTop=(listh-thumbSize)/2;
					var imgscr=datas[i].thumb?datas[i].thumb:datas[i].img;
					var iimg=$("<img src='"+imgscr+"'>").css({"width":thumbSize+"px","height":thumbSize+"px"});
					//下面代码是li平均分布
					//var infow=(barpos=="bottom"||barpos=="top")?mw:barsize;
					//var mar=(infow-listh*datas.length)/(datas.length-1);
  					//$.extend(cssobj,{"margin":i == 0 ? "0px":"0px 0px 0px "+mar+"px","width":imgh+"px","height":imgh+"px","display":"inline"});
			    	bari.html(iimg);
			    	$.extend(cssobj,{"width":thumbSize+"px","height":thumbSize+"px"});
				break;
			}
			$.extend(cssobj,i==curidx?bar_css_on:bar_css_off);
			bari.css(cssobj);
			//根据边框调整位置
			var borderH=(parseInt(bari.css("borderTopWidth"))+parseInt(bari.css("borderBottomWidth")))||0;
   			bari.css("marginTop",marginTop-borderH/2);
  			//当前点击
			bari.bind(actionEvent,{idx:i},function(e){
 					scrollto(e.data.idx);
			}).appendTo(bar);				
			bars[bars.length]=bari;
		}

 		titleback.appendTo(infobar);
 		barback.appendTo(infobar);
		bar.appendTo(infobar);
		title.appendTo(infobar);
		titleback.appendTo(infobar);
		infobar.appendTo(wraper);
		if(!hastitle){
			infobar.css("display","none");
		}

		scroller.appendTo(wraper);
		wraper.appendTo(container);
		
		var timer=0;
 		//scroll
 		function scrollto(idx){
 			idx=idx>shows.length-1?0:idx;
 			idx=idx<0?shows.length-1:idx;
			if(idx!=curidx){
				if(timer){clearTimeout(timer);}
				switch(animateStyle){
					case "linear":
						var scrollleft=shows[idx].getBox().left-scroller.getBox().left;
						scroller.animate({left:-scrollleft}, "slow");
					break;
					default:
						shows[curidx].fadeOut("normal","linear");
						shows[idx].delay(10).fadeIn("normal","linear");
					break;
				}
 				if(hastitle){
					bars[curidx].css(bar_css_off);
					bars[idx].css(bar_css_on);
					title.html(datas[idx].title);
				}
				
 				curidx=idx;
				timer=setTimeout(autoplay,autoPlayTime);
			}
   		}
		//autoplay
		function autoplay(){
  			scrollto(curidx+1);
		}
		if(autoplay){
			timer=setTimeout(autoplay,autoPlayTime);
		}
	}
 	
	function efloat(setting){
		if(!setting||!setting.selector){
			alert("对象不能为空");
		}
		var mobj=$(setting.selector);
		var htype=setting.htype?setting.htype:"center";
		var vtype=setting.vtype?setting.vtype:"middle";
		var offsetx=setting.offsetx?setting.offsetx:0;
		var offsety=setting.offsety?setting.offsety:0;		
		var floatcondition=setting.condition?setting.condition:"always";
		var basebox=mobj.getBox();
		var canfixed=!$.browsers.isIE6;
		var border_top=parseInt(mobj.css("borderTopWidth"));
		var border_left=parseInt(mobj.css("borderLeftWidth"));
	
 		if(floatcondition=="over"){
 			var placeholder=mobj.clone(true,true).css("display","none");
			placeholder.insertBefore(mobj);
			$(window).bind("scroll resize",{"base":mobj,"place":placeholder},function(e){
				//判断是否滚动显示
				var curbox=e.data.base.getBox();
				if(curbox.top<=0){
					e.data.place.css("display","block");
				}else{
					e.data.place.css("display","none");
				}
			});
			keeppos(placeholder);
		}else{
			keeppos(mobj);
		}
		
		function keeppos(jelement){
			jelement.css("position",canfixed?"fixed":"absolute");
			setpos(jelement);
			if(!canfixed||htype=="center"||vtype=="middle"||vtype=="fixed"||htype=="fixed"){
				$(window).bind("scroll resize",jelement,function(e){
						setpos(e.data);
				});
			}
		}
		function setpos(jelement){
			var win=$.getWinBox();
			var obox=jelement.getBox();
			var scrollLeft=$(window).scrollLeft();
			var scrollTop=$(window).scrollTop();
			var pos={};
			switch(htype){
				case "left":
					pos.left=canfixed?0:scrollLeft;	
				break;
				case "right":
					if(canfixed){
						pos.right=0;
					}else{
						pos.left=win.browser.width+scrollLeft-obox.width;
					}
				break;
				case "self":
					pos.left=canfixed?basebox.left:basebox.left+scrollLeft-border_left;	
				break;
				case "fixed":
					pos.left=canfixed?basebox.left-scrollLeft:basebox.left-border_left;
				break;
				case "center":
				default:
					pos.left=(win.browser.width-obox.width)/2;
					pos.left=canfixed?pos.left:pos.left+scrollLeft;
				break;
			}
			switch(vtype){
				case "top":
					pos.top=canfixed?0:scrollTop;
				break;
				case "bottom":
					if(canfixed){
						pos.bottom=0;
					}else{
						pos.top=win.browser.height+scrollTop-obox.height;
					}
				break;
				case "self":
 					pos.top=canfixed?basebox.top:basebox.top+scrollTop-border_top;
				break;
				case "fixed":
					pos.top=canfixed?basebox.top-scrollTop:basebox.top-border_top;
				break;
				case "middle":
				default:
					pos.top=(win.browser.height-obox.height)/2;
					pos.top=canfixed?pos.top:pos.top+scrollTop;
				break;
			}
			if(pos.top!=undefined){pos.top+=offsety;}
			if(pos.bottom!=undefined){pos.bottom-=offsety;}
			if(pos.left!=undefined){pos.left+=offsetx;}
			if(pos.right!=undefined){pos.right-=offsetx;}
 			jelement.css(pos);
		}	
	}
 	 function tips(setting){
		var mobj=$(setting.selector);
		var info=setting.info?setting.info:"";
		var condition=setting.condition?setting.condition:"over";
		var dataurl=setting.dataurl?setting.dataurl:false;
		var loadonce=setting.loadonce!=undefined?setting.loadonce:true;
		var classname=setting.classname?setting.classname:"";
		var mode=setting.mode?setting.mode:"border";
		var keep=setting.keep!=undefined?setting.keep:false;
		var hasbar=setting.hasbar!=undefined?setting.hasbar:false;
		var title=setting.title!=undefined?setting.title:"";

		var tipobj=$("<div>").css({"position":"absolute","display":"none"}).appendTo($(document.body));
		if(classname==""){
			tipobj.css({"borderWidth":"8px 10px 15px 5px","borderColor":"#0f0","borderStyle":"solid","backgroundColor":"#fff"});
		}else{
			tipobj.addClass(classname);
		}

		if(hasbar){
			var titlebar=$("<dl>").css({"margin":0, "padding":0,"position":"relative","height":"30px","line-height":"30px","background-color":"rgb(251,237,187)"});
			var title=$("<dt>").css({"margin":0, "padding":0,"float":"left","margin-left":"10px","color":"rgb(235,110,0)","font-weight":"bold","font-size":"15px"}).html(title);
			var closebtn=$("<dd>").css({"wdith":"20px","height":"20px","line-height":"20px","float":"right","position":"absolute","top":"5px","right":"5px","text-align":"center","vertical-align":"middle","font-size":"18px","cursor":"pointer","background-color":"rgb(235,110,0)","color":"#fff"}).html("×").click(function(e) {
				tipobj.hide();
			});
			title.appendTo(titlebar);
			closebtn.appendTo(titlebar);
			titlebar.appendTo(tipobj);
		}
		tipobj.append(info);
		var contentobj=$("<div>").css("line-height","20px").appendTo(tipobj);
		
		if(dataurl){tipobj.attr("data-url",dataurl)}
		switch(condition){
			case "click":
				mobj.click(
					function(e){
						tipobj.toggle();
						gethtml();
						setpos();
					} 
				);
			break;
			case "over":
			default:
				mobj.hover(
					function(e){
						tipobj.show();
						gethtml();
						setpos();
					},function(e){
						tipobj.hide();
					}
				);
			break;
		}
		if(keep){
			$(window).bind("resize scroll",function(){
				setpos();
			});
		}
		
		function  gethtml(){
			if(tipobj.attr("data-url")){
				$.get(tipobj.attr("data-url"),function(s){
					contentobj.html(s);
					setpos();
					tipobj.css({width:tipobj.width(),height:tipobj.height()});
					if(loadonce){tipobj.removeAttr("data-url");}
				});
			}
		}
		function setpos(){
				var pos=getRoundPos(mobj,tipobj,setting.vtype,setting.htype,mode);
				tipobj.css(pos);
		}
 	}
	
	function menu(s){
		var defcssjson={"margin":0,"padding":0,"list-style":"none","float":"left"};
		var orient=s.orient?s.orient:"h";
		var animation=s.animation?s.animation:"none";
		var childtype=s.childtype?s.childtype:"";
		var mobj=$(s.selector).css(defcssjson);
		
		 mobj.find("ul").css(defcssjson);
		 $.extend(defcssjson,{"clear":"barsize","position":"relative"});
		 mobj.find("li").css(defcssjson).hover(
											  function () {
												  switch(animation){
													  case "slide":
															$(this).children("ul").slideDown("fast");
													  break;
													  case "fade":
															$(this).children("ul").fadeIn("fast");
													  break;
													  default:
															$(this).children("ul").css({"display":"block"});
													  break;
												  }
											  },
											  function () {
												  switch(animation){
													  case "slide":
															$(this).children("ul").slideUp("fast");
													  break;
													  case "fade":
															$(this).children("ul").fadeOut("fast");
													  break;
													  default:
															$(this).children("ul").css({"display":"none"});
													  break;
												  }
											  }
											);
		 mobj.find("a").css({"text-decoration":"none","display":"block"});
		 if(orient=="h"){mobj.children("li").css("clear","none");} 
		 mobj.find("li ul").css({"position":"absolute","z-index":100,"top":0,"white-space":"nowrap"});		
		 mobj.find("li").each(function(){
			  if($(this).parent("ul").get(0)!=mobj.get(0)){
				  var borderw=parseInt($(this).css("border-left-width"))||0+parseInt($(this).css("border-right-width"))||0;
					 $(this).css("width",$(this).parent().width()-borderw);
					 $(this).children("ul").css({"left": $(this).width()});
			  }else{
				  if(orient=="v"){
					  $(this).children("ul").css({"left": $(this).width()});
				  }else{
					  $(this).children("ul").css({"top": $(this).children().first().height()});
				  }
			  }
			   if(childtype=="box"){
					var mbox=mobj.getBox();
					var cbox=$(this).getBox();
				   if(orient=="v"){
					   $(this).children("ul").css({"top": mbox.top-cbox.top,"height":mobj.height()});
				   }else{
					   if($(this).parent("ul").get(0)!=mobj.get(0)){
							$(this).children("ul").css({"top": $(this).height()});
					   }
					   $(this).children("ul").css({"left": mbox.left-cbox.left,"width":mobj.width()});
					}
			   }
		 });
		   mobj.find("li ul").css({"display":"none"});
	}

	//获取元素环绕另外对象元素周边显示的位置，返回left top  json数据
	function getRoundPos(baseobj,tipobj,vtype,htype,mode){
		htype=htype?htype:"center";
		vtype=vtype?vtype:"middle";
 		
		var obox=baseobj.getBox();
		var scrollLeft=$(window).scrollLeft();
		var scrollTop=$(window).scrollTop();
		var border_top=parseInt(baseobj.css("borderTopWidth"))||0;
		var border_left=parseInt(baseobj.css("borderLeftWidth"))||0;
		var border_bottom=parseInt(baseobj.css("borderBottomWidth"))||0;
		var border_right=parseInt(baseobj.css("borderRightWidth"))||0;
		
		var tip_left=parseInt(tipobj.css("borderLeftWidth"))||0;
		var tip_right=parseInt(tipobj.css("borderRightWidth"))||0;
		var tip_top=parseInt(tipobj.css("borderTopWidth"))||0;
		var tip_bottom=parseInt(tipobj.css("borderBottomWidth"))||0;

 		var oleft=otop=0;
 		switch(htype){
			case "left":
 				oleft=obox.left;
				oleft=mode=="content"?oleft-tip_left+border_left:oleft;
				oleft=$.browsers.isIE6?oleft-2:oleft;
			break;
			case "right":
 				oleft=obox.right-tipobj.width()-tip_left-tip_right;
				oleft=mode=="content"?oleft+tip_left-border_right:oleft;
				oleft=$.browsers.isIE6?oleft-2:oleft;
  			break;
			case "out_left":
 				oleft=obox.left-tipobj.width()-tip_left-tip_right;	
				oleft=mode=="content"?oleft+tip_left+border_left:oleft;
				oleft=$.browsers.isIE6?oleft-2:oleft;
			break;
			case "out_right":
  				oleft=obox.right;
				oleft=mode=="content"?oleft-tip_left-border_right:oleft;
				oleft=$.browsers.isIE6?oleft-2:oleft;
 			break;
			case "center":
			default:
				oleft=obox.left+(obox.width-tipobj.width())/2-tip_left;
			break;
		}
		switch(vtype){
 			case "top":
 				otop=obox.top;
				otop=mode=="content"?otop-tip_top+border_top:otop;
				otop=$.browsers.isIE6?otop-2:otop;
 			break;
 			case "bottom":
 				otop=obox.bottom-tipobj.height()-tip_top-tip_bottom;
				otop=mode=="content"?otop+tip_top-border_bottom:otop;
				otop=$.browsers.isIE6?otop-2:otop;
  			break;
			case "out_top":
 				otop=obox.top-tipobj.height()-tip_top-tip_bottom;
				otop=mode=="content"?otop+tip_top+border_top:otop;
				otop=$.browsers.isIE6?otop-2:otop;
			break;
			case "out_bottom":
 				otop=obox.bottom;
				otop=mode=="content"?otop-tip_top-border_bottom:otop;
				otop=$.browsers.isIE6?otop-2:otop;
 			break;
			case "middle":
			default:
				otop=obox.top+(obox.height-tipobj.height())/2-tip_top;
			break;
		}
		return {"left":oleft+scrollLeft,"top":otop+scrollTop};
	}

	function dragable(s){
  		$(s.selector).each(function(index, element) {
            element.onselectstart=function(){return false;}
        });
 		var startx,starty,mobj,cpos,offset,canmove=false;
		$(s.selector).bind("mousedown",function(e){
				e.preventDefault();
				//鼠标初始坐标
				startx=e.clientX;
				starty=e.clientY;
 				//当前位置
				cpos=$(this).position();
				
     			canmove=true;
 				mobj=this;
   				$(document.body).bind("mousemove",drag);
				$(document.body).bind("mouseup",function(){
					canmove=false;
					$(document.body).unbind("mousemove",drag);
				});
		});
		
		
		function drag(e){
			if(canmove){
 				//鼠标移动的偏移量
 				var movex=e.clientX-startx+cpos.left;
				var movey=e.clientY-starty+cpos.top;
   				
 				switch(s.stype){
					case "h":
 							$(mobj).css({"position":"absolute","cursor":"move","z-index":100,"left":movex,"top":cpos.top});
					break;
					case "v":
							$(mobj).css({"position":"absolute","cursor":"move","z-index":100,"top":movey,"left":cpos.left});
					break;
					default:
							$(mobj).css({"position":"absolute","cursor":"move","z-index":100,"left":movex,"top":movey});
					break;
				}
			}
		}
	}
	 function edialog(sets){
 		var title=sets.title||"";
		var boxwidth=sets.width==undefined?360:parseInt(sets.width);
		var boxheight=sets.height==undefined?250:parseInt(sets.height);
		var closepic=sets.closepic;
		var content=sets.content;

		var boxid='cczw_dialog_id';
		var boxborder=10;

		//总box
		var	mbox=$("<div id='"+boxid+"'></div>");
			mbox.css({"width":boxwidth+"px","height":boxheight+"px","zIndex":10000});
		//透明背景
		var alphabox=$("<div></div>").css({"position":"absolute","top":"0px","left":"0px","width":boxwidth+"px","height":boxheight+"px","backgroundColor":"#333","opacity":"0.2"}).appendTo(mbox);
		//mainbox
		var mainbox=$("<div></div>").css({"position":"absolute","top":boxborder+"px","left":boxborder+"px","width":(boxwidth-boxborder*2)+"px","height":(boxheight-boxborder*2)+"px","backgroundColor":"#fff"}).appendTo(mbox);
		
		//titlebar
		var mtitlebar=$("<dl></dl>").css({"margin":"0px","height":"37px","line-height":"37px","background":"#F2F2F2","border-bottom":"1px solid #dedede"}).appendTo(mainbox);
		//title
		var title=$("<dt>"+title+"</dt>").css({"float":"left","margin-left":"10px","font-size":"14px","color":"#f33b3b"}).appendTo(mtitlebar);
		//closebtn
		var btnhtml=closepic?"<img src='"+closepic+"' style='vertical-align:text-bottom' />":"×";
		var closebtn=$("<dd>"+btnhtml+"</dd>").css({"float":"right","margin-top":"5px","margin-right":"5px","cursor":"pointer"}).bind("click",mbox,function(event){
			event.data.remove();
		}).appendTo(mtitlebar);

		//内容部分
		var mcontent=$("<div></div>").appendTo(mainbox);
		if(content){mcontent.append(content);}

		mbox.appendTo("body");

		return {box:mbox,title:title,content:mcontent};
   	}

   	function paging(setting){
		//样式相关
		var wrap=$(setting.selector);
		var def_size=setting.size==undefined?20:parseInt(setting.size);
		var str_size=def_size+"px";
		var pn_size=(def_size+4)+"px";
		var btn_css=setting.btnCss==undefined?{"display":"inline","line-height":str_size,"font-size":"14px","color":"#003399","float":"left","border":"1px solid #ccc","padding":"1px 3px","cursor":"pointer","background":"#fff"}:setting.btnCss;
		var pageNumer_css_def={"display":"inline","margin":"0px 5px","width":pn_size,"height":pn_size,"font-size":"14px","text-align":"center","line-height":pn_size,"cursor":"pointer"}
		var pageNumber_css_focus=setting.pageNumberCssFocus==undefined?{"color":"#ffffff","background":"#698B22","float":"left"}:setting.pageNumberCssFocus;
			$.extend(pageNumber_css_focus,pageNumer_css_def);
		var pageNumber_css_blur=setting.pageNumberCssBlur==undefined?{"color":"#003399","background":"none","float":"left"}:setting.pageNumberCssBlur;
			$.extend(pageNumber_css_blur,pageNumer_css_def);


		//数据相关
		var pagecount=setting.pageCount==undefined?0:parseInt(setting.pageCount);
		var curpage=setting.curPage==undefined?1:parseInt(setting.curPage);
		var shownum=setting.showNum==undefined?5:parseInt(setting.showNum);	//显示的页码数量
			shownum=shownum>pagecount?pagecount:shownum;
		//回调 param:pageno
		var func=setting.func;

		//build ui
		var context=$("<div>").css({"text-align":"center","float":"left","margin-left":"154px","display":"inline"});
		refresh(curpage);
		context.appendTo(wrap);


		function refresh(pageno){
			context.html("");
			curpage=pageno;
			var pno_first=parseInt(curpage-shownum/2);
			pno_first=pno_first+shownum>pagecount?pagecount-shownum+1:pno_first;
			pno_first=pno_first<=0?1:pno_first;

			var btn_prev=$("<div style='float:left'><div style='float:left;border-color: transparent #ccc transparent transparent;border-style: dashed solid dashed dashed; border-width: 4px 4px 4px 0px;width: 0;height:0;overflow:hidden;margin:6px;margin-left:5px'></div><div style='float:left;margin:0px 5px 1px 0px'>上一页</div></div>").css(btn_css).bind("click",function(){
				if(curpage<=1){return false;}
				curpage=curpage-1;
				if(func){
					refresh(curpage);
					func(curpage);
				}

			}).appendTo(context);
			for(var i=0;i<shownum;i++){
				var no=pno_first+i;
				var css=no==curpage?pageNumber_css_focus:pageNumber_css_blur;
				var pagen=$("<span>"+no+"</span>").css(css).appendTo(context);
				if(func){
					pagen.bind("click",no,function(e){refresh(e.data),func(e.data)})
				}
			}
			
			
			if(pno_first+shownum<=pagecount){
				var pagen=$("<span>... "+pagecount+"</span>").css(pageNumber_css_blur).css("width","auto").appendTo(context);
				if(func){
					pagen.bind("click",pagecount,function(e){refresh(e.data),func(e.data)})
				}
			}
			var btn_next=$("<div style='float:left'><div style='float:left;display:block;margin:1px 6px 0px 5px'>下一页</div><div style='border-color: transparent  transparent transparent #ccc;border-style: dashed dashed dashed solid;border-width: 4px 0px 4px 4px; width: 0; height: 0;overflow:hidden;margin: 6px;float:left;margin-right:4px'></div></div>").css(btn_css).bind("click",function(){
				if(curpage>=pagecount){return false;}
				curpage=curpage+1;
				if(func){
					refresh(curpage);
					func(curpage);
				}
			}).appendTo(context);
			if(curpage<=1){
				btn_prev.css({"color":"#bcbcbc","border":"1px solid #e7e7e7"});
			}else{
				btn_prev.mouseover(function(){
					$(this).css("background","#ebeff7");
				}).mouseout(function(){
					$(this).css("background","#fff");
				})
			}
			if(curpage>=pagecount){
				btn_next.css({"color":"#bcbcbc","border":"1px solid #e7e7e7"});
			}else{
				btn_next.mouseover(function(){
					$(this).css("background","#ebeff7");
				}).mouseout(function(){
					$(this).css("background","#fff");
				})
			}
			
		}
	}
 /**************************************扩展jQuery对象本身***************************************/
	jQuery.extend({
		browsers:{
				isIE:isIE,/**排除搜狗和遨游和qq浏览器，360就不用排除了基本根ie一样，所以就当ie了，方便统一兼容*/
				isIE6:isIE6,
				isFirefox:isFirefox,
				isOpera:isOpera,
				isSafari:isSafari,
				isChrome:isChrome,
				isQQ:isQQ,
				is360:is360,
				isSogou:isSogou,
				isMaxthon:isMaxthon,
				isMaxthonHightModen:isMaxthonHightModen,
				isIECore:isIECore,
				isWebkitCore:isWebkitCore,
				basedomain:document.domain,
				getMainDomain:getUrlMainDomain
		},
  		/**小图标*/
	 	icons:["●","○","□","■","◆","◇","▲","▶","▼","◀","△","▷","◁","▽","★","☆","☺","☻","♥","♡","→","←","↑","↓","↔","↘","↖","↗","↙","⊕","⊙","⊿","−","⊥","−","÷","×","=","+"],
		/**获取窗口的大小信息*/
		getWinBox:function(){
 			var mbrower={width:0,height:0};
			var bodybox;
			if($.browser.msie){
				mbrower.height=$.support.boxModel? document.documentElement.clientHeight : document.body.clientHeight;
				mbrower.width=$.support.boxModel? document.documentElement.clientWidth : document.body.clientWidth; 
				var box=$.support.boxModel?document.body.getBoundingClientRect():document.documentElement.getBoundingClientRect();
			} 
			else { 
				mbrower.width=self.innerWidth;
				mbrower.height=self.innerHeight;
				mbody=document.documentElement.getBoundingClientRect();
			}
			mbody=($.support.boxModel&&$.browser.msie)?document.body:document.documentElement;
  			return {browser:mbrower,body:$(mbody).getBox()};
		},
		//cookie操作类
		cookie:{
			get:function getCookie(key) {
				var tmp =  document.cookie.match((new RegExp(key +'=[a-zA-Z0-9.()=|%/_]+($|;)','m')));
				if(!tmp || !tmp[0]) return "";
				else return decodeURI(tmp[0].substring(key.length+1,tmp[0].length).replace(';','')) || "";
			},
			set:function setCookie(name,value){
				var argv = arguments;
				var argc = argv.length;
				var expires = (argc > 2) ? argv[2] : null;
				var path = (argc > 3) ? argv[3] : null;
				var domain = (argc > 4) ? argv[4] : null;
				var secure = (argc > 5) ? argv[5] : false;
				document.cookie = name + "=" + escape (value) +
				((expires == null) ? "" : ("; expires=" + expires.toUTCString())) +
				((path == null) ? "" : ("; path=" + path)) +
				((domain == null) ? "" : ("; domain=" + domain)) +
				((secure == true) ? "; secure" : "");
			}	
		},
		/**
		*获取跨域jquery对象,
		*crossurl   目标子域上的跨域静态文件，该文件 1，必须包含jquery文件，2必须设置document.domain为主域名
		*注意，本函数会更改本页面的域名为主域名，如果有cookie或者其它跟域名相关的操作请慎用
		*/
		getCrossJQuery:function(crossurl){
			if($.browsers.basedomain!="home.meishichina.com"){
			var crossiframeid="cross_iframe_id";
            var crossiframe=$("#"+crossiframeid);
            if(!crossiframe[0]){
                crossiframe=$("<iframe id='"+crossiframeid+"' src='"+crossurl+"' />").appendTo("body");
            }
            document.domain=getUrlMainDomain(this.browsers.basedomain);
            return crossiframe[0].contentWindow.$;
			}
		},
		/**query数据解析成为json数据,如果有同名属性，值取最后一个
		 *@param strQuery url ?后面的对象,不包括？
		 *@param checkValueType 是否检查值的类型，默认为string
		 *@return json
		 * */
		queryToJson:queryToJson,
		 /**多级联动
		 * @param setting   json数据与selelct的说明映射json  详情如下		
			{   jsonData:{},    		//* json数据联动
				keyValue:"",			//* option的value对应的字段
				keyText:"",      		//* option的显示text对应的字段
				selsids:[]				//* 对应的select的id列表
				selskeys:[],   			//* json数据与selsids对应的select层级相对应的key，与这里的每一项对应的值也都是一个数组
				showValues:[]			//* select的当前选中对应的值列表
			}	
		 */
 		multiSelect:function(setting){
			setting=setting?setting:{};
 			new buildMultiSelect(setting);
		},
		 /**
		 *说明:对话框
		 @param setting json格式参数 详情如下:
		 {
			title: 		标题，
			width: 		dialog宽度,
			height: 	dialog的高度
			closepic:   关闭按钮图片地址，默认x
			content:    content内容部分的jquery dom节点
		 }
		 */
	 	edialog:function(setting){
	 		setting=setting?setting:{};
	 		return edialog(setting);
	 	}
 	});
/*********************************扩展 jQuery 元素集来提供新的方法*****************************/
	jQuery.fn.extend({
 		/**获取当前元素的盒模型信息，left，top,width,height,right,bottom， 不同于jquery原生计算函数,边框被计算在内，*/
		getBox:function(){
 			var box=$(this).get(0).getBoundingClientRect();
			var  mbox={};
			mbox.left=box.left;
			mbox.right=box.right;
			mbox.top=box.top;
			mbox.bottom=box.bottom;
			mbox.width=box.right-box.left;
			mbox.height=box.bottom-box.top;
			return mbox;
		},
 		/** ajax tabs
	 * @param setting  json形式的参数 详情如下:
	 	{
			showidx	:			//初始化显示的tab索引   -1的时候不做任何操作
			onClass				//当前tab的样式
			offClass			//其它tab的样式
			loading				//ajax的loading等待html
			rollback 			//鼠标离开后是否回滚，默认false, （当前检测只是鼠标如果在当前selector的父容器区域，不是很通用）
		}
	 */
		tabs:function(setting){
 			setting=setting?setting:{};
			setting.selector=this;
			tabs(setting);
		},
		/** ajax slider  幻灯片
		 * @param setting  json形式的参数 详情如下:
			{
				datas:[]			//**数据数组,每个单元数据格式为json格式={"title":"标题","url":"点击跳转的url","img":"显示的图片地址","thumb":"缩略图地址"}
				autoPlay：			//是否自动播放，默认true
				actionEvent 		//触发事件类型,click(默认),mouseover...
				autoPlayTime:		//自动播放过渡时间,默认5000毫秒,
				animateStyle: 		//动画类型,linear ，fade(默认)
				hasTitle: 			//是否显示底部状态栏 默认true
				barStyle: 			//底部状态栏的样式  line,number,images(默认)
				thumbSize			//底部状态栏的样式如果是images则该项有效，代表缩略图的大小，如果没有则根据状态栏大小自动调整
				barPosition			//底部状态栏的显示方位： left,right,top,bottom(默认)
				barSize 		    //底部状态栏的大小,当barPosition为垂直位置时该值代表的是高度，水平位置时是宽度
				barAlign			//底部状态栏内容布局方式，left.center(默认),right
				titleBarH 			//说明信息栏的高度
				titleBarBackImg 	//说明信息栏的背景图片
				titleBarBackColor	//说明信息栏的背景颜色，仅当titleBarBackImg没有设置时有效
				titleBarFontColor   //说明信息栏的背景颜色，仅当titleBarBackImg没有设置时有效
				titleBarBackAlpha	//说明信息栏的背景颜色透明度,默认0.5
				listBackImg  		//列表栏的背景图片
				listBackColor	    //列表栏的背景颜色
				listBarBackAlpha	//列表栏的背景颜色透明度,默认0.5
				listBackFontColor   //列表栏的字体颜色
				barCssOn 			//列表栏当前项的样式json格式{}
				barCssOff           //列表栏非当前项的样式son格式{}
 			}
		 */
		 slider:function(setting){
			 setting=setting?setting:{};
			 setting.selector=this;
			 slider(setting);
		 },
		
		 /**
		*	浮动
		* @param setting  json形式的参数 详情如下:
		{
 			 htype  			水平浮动类型:left,center(默认),right, self(当前元素的水平位置值),fixed（保持在当前的页面布局水平位置）
			 vtype   			垂直浮动类型:top,middle(默认),bottom, self(当前元素的水平位置值)，fixed（保持在当前的页面布局垂直位置）
			 offsetx			附加的水平偏移量  默认0
			 offsety			附加的垂直偏移量  默认0
 			 condition			浮动条件always(默认)总是浮动,over当该元素将被卷起时浮动
		}
		*/
		efloat:function(setting){
			setting=setting?setting:{};
			setting.selector=this;
			efloat(setting);
		},
		 /**
		*	提示
		* @param setting  json形式的参数 详情如下:
		{
  			 htype  			水平浮动类型:left,center(默认),right
			 vtype   			垂直浮动类型:top,middle(默认),bottom
  			 info:				显示的内容
			 dataurl:			ajax内容获取地址	
			 loadonce:			内容是否只自动加载一次 默认true		
			 condition			over(默认)，click点击
 			 classname     		提示框的样式
	 		 moden				对齐模式content依据内容区域对齐（默认），border依据边框对齐
			 hasbar				是否包含默认的toolbar 关闭按钮
			 keep				是否根据滚动和窗口改变实时刷新，默认false，如果根元素不是浮动元素，请不要设置该项
			 title				title内容
  		}
		*/
		tips:function(setting){
			 setting=setting?setting:{};
			 setting.selector=this;
			 tips(setting);
		},
		/**
		获取元素环绕另外对象元素周边显示的位置，返回left top  json数据
		*/
		getRoundPos:function(tipobj,vtype,htype,mode){
			 return getRoundPos($(this),tipobj,vtype,htype,mode);
		},
		/**菜单
		 * @param setting   json数据与selelct的说明映射json  详情如下		
			{   
				animation  动画：none(默认),slide(拉伸),fade(渐隐渐现)
				orient		方向：h(默认)横向，v纵向
				childtype	下级菜单的模式:（默认)普通列表方式，box(盒模式)*
			}	
	 	*/
		menu:function(setting){
			 setting=setting?setting:{};
			 setting.selector=this;
			 menu(setting);
		},
		/**拖拽
		  * @param s  json形式的参数 详情如下:
		{
			 stype   拖动样式：(默认)无限制，h:水平，v垂直
		 }
		 */
		 dragable:function(setting){
			setting=setting?setting:{};
			setting.selector=this;
			dragable(setting);
		},
		/**分页
		 * @param setting   json数据与selelct的说明映射json  详情如下		
			{   
				size  		页码框大小，默认20px
				btnCss 		上一页，下一页样式 json格式
				pageNumberCssFocus  页码框选中时的样式 json格式
				pageNumberCssBlur   页码框未选中时的样式 json格式
				pageCount 			总页数
				curpage             当前页数
				shownum 			同时现实的页码数
				func 				点击页码时的回调，带一个页码参数
			}	
		*/
		paging:function(setting){
			setting=setting?setting:{};
			setting.selector=this;
			paging(setting);
		}
	});
	
})(jQuery);