import CryptoJS from 'crypto-js'
import config from './mall.config.js';

export const formatURL = (method,_apiOptions) => {
	const appsecret = config.AppSecret

	let _time = new Date();

	// 时间戳
  let _timestamp = `${_time.getFullYear()}-${_time.getMonth()+1<10?'0'+(_time.getMonth()+1):_time.getMonth()+1}-${_time.getDate()<10?'0'+_time.getDate():_time.getDate()} ${_time.getHours()<10?'0'+_time.getHours():_time.getHours()}:${_time.getMinutes()<10?'0'+_time.getMinutes():_time.getMinutes()}:${_time.getSeconds()<10?'0'+_time.getSeconds():_time.getSeconds()}`

  let systemOptions = {
      method: method,
      timestamp: _timestamp,
      format: 'json',
      app_key: config.appKey,
      v: '2.0',           
      sign_method: 'md5'
  }
  let apiOptions = {}
  if(_apiOptions) {
    apiOptions = _apiOptions
  }
  

  //合并排序
  let _optionsArr = []

  for(let i in systemOptions) {
      _optionsArr.push(i + systemOptions[i])
  }

  for(let j in apiOptions) {
      _optionsArr.push(j + apiOptions[j])
  }
  _optionsArr.sort()

  let _optionsStr = _optionsArr.join('')

  let _finalOptions = appsecret + _optionsStr + appsecret

  let sign = CryptoJS.MD5(_finalOptions).toString().toUpperCase()

  let url = `http://gw.api.taobao.com/router/rest?sign=${sign}`
  for (let i in systemOptions) {
    url += `&${i}=${encodeURIComponent(systemOptions[i])}`
  }

  for(let j in apiOptions) {
    url += `&${j}=${encodeURIComponent(apiOptions[j])}`
  }
  url = url.replace(/\%20/g,'+')

  return url
}