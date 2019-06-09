//app.js
App({
  onLaunch: function () {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    wx.setStorageSync('questionList', [])

    // 登录
    var that = this
    wx.login({
      success: res => {
        var loginUrl
        if(res.code) {
          loginUrl = 'https://moneydog.club:3030/Create/Login?code=' + res.code
        }
        //登录时获取sessionID
        wx.request({
          url: loginUrl,
          method: 'POST',
          header: {
            "Content-Type": "application/x-www-form-urlencoded"
          },
          success: function (res) {
            console.log(res)
            if (res.data.errcode == 1) {
              console.log("登录时获取的SessionId：" + res.data.SessionId)
              that.globalData.sessionID = res.data.SessionId//获取sessionID并保存在全局变量sessionID中
              wx.setStorageSync('SessionId', res.data.SessionId)
            }
            else {
              console.log('用户未注册')
              wx.login({
                success: res => {
                  if (res.code) {
                    that.globalData.code = res.code
                  }
                }
              })
            }
          }
        })
      }
    })
    
    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo

              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        }
      }
    })
  },
  globalData: {
    userInfo: null,
    sessionID: null,
    code: null
  }
})