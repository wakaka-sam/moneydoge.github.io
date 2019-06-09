const app = getApp()
const baseUrl = 'https://moneydog.club:3030/'
// pages/details.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    sessionId: '847694c4-14dd-47b2-8922-facd8e379f47',
    showDialog: false,
    id: 0,//1:快递 2:跑腿 3:求助 4:闲置 5:问卷
    json: [],
    qid: 0,//问卷id
    name: '调查问卷',
    description: '饭堂满意度',
    content: [
      {"type":0,"title":"对饭堂的满意度", "a":"不满意","b":'一般',"c":'较满意',"d":'满意'},
      {"type":1,"title":"喜欢窗口", "a":"不满意","b":'一般',"c":'较满意',"d":'满意'},
      {"type":2,"title":"对饭堂的建议","a":"","b":'',"c":'',"d":''}],
    content_count: [
      {type:0,a:0,b:0,c:0,d:0,fill:''},
      {type:1,a:0,b:0,c:0,d:0,fill:''},
      {type:2,a:0,b:0,c:0,d:0,fill:''}
    ],
    imgurl: 'a58e416d-64fd-444e-854b-9d36e3ae27291.jpg'
  },

  //单选题选择选项
  radioChange: function (e) {
    var index = e.currentTarget.dataset.id 
    var value = e.detail.value
    var content_count = this.data.content_count
    content_count[index] = {
      type:0,
      a:value=='a'?1:0,
      b:value=='b'?1:0,
      c:value=='c'?1:0,
      d:value=='d'?1:0,
      fill:''
    }
    //console.log(content_count[index])
    this.setData({content_count: content_count})
  },
  //多选题选择选项
  checkboxChange: function (e) {
    var index = e.currentTarget.dataset.id 
    var value = e.detail.value
    var content_count = this.data.content_count
    content_count[index] = {
      type:1,
      a:value.includes('a')?1:0,
      b:value.includes('b')?1:0,
      c:value.includes('c')?1:0,
      d:value.includes('d')?1:0,
      fill:''
    }
    //console.log(content_count[index])
    this.setData({content_count: content_count})
  },
  //填空题输入获取
  inputboxChange: function (e) {
    var index = e.currentTarget.dataset.id 
    var value = e.detail.value 
    var content_count = this.data.content_count
    content_count[index] = {
      type:2,
      a:0,
      b:0,
      c:0,
      d:0,
      fill:value+';'
    }
  },
  //提交问卷
  fill: function () {
    var that = this
    var qid = that.data.qid
    var content_count = that.data.content_count
    var completed = true

    for (var i = 0; i < content_count.length; i++) {
      var item = content_count[i]
      console.log(item)
      if (item.a=='' && item.b=='' && item.c=='' && item.d=='' && item.fill=='') {
        completed = false
      }
      else if (item.a=='' && item.b=='' && item.c=='' && item.d=='' && item.fill==';') {
        completed = false
      }
    }
    console.log(completed)
    if (completed) {
      content_count = JSON.stringify({content_count:content_count})
      console.log(content_count, qid)
      wx.request({
        url: baseUrl + 'Create/fill',
        data: {
          content_count: content_count,
          id: qid
        },
        method: 'POST',
        header: {
          sessionId:that.data.sessionId,
          "Content-Type": "application/x-www-form-urlencoded"
        },
        success: function (res) {
          console.log(res)
          console.log(res.data.errcode,res.data.errmsg)
          if (res.data.errcode == 1) {
            wx.navigateBack({delta: 1})
          }
          else if(res.data.errcode == 0){
            wx.showToast({
              title: '填写失败',
              icon: 'none',
              duration: 3000
            })
          }
          else if(res.data.errcode == 2){
            wx.showToast({
              title: '你已填写过此问卷',
              icon: 'none',
              duration: 3000
            })
          }
        }
      })
    }
    else {
      wx.showToast({
        title: '未完成填写',
        icon: 'none',
        duration: 2000//持续的时间
      })
    }
  },

  //获取联系方式
  getContact: function (e) {
    var that = this
    var options = e.currentTarget.dataset
    console.log(options)
    var url = baseUrl + 'Load/Contact_way?type=' + 
              options.type + '&id=' + options.id
    console.log(url, 'sessionId:', that.data.sessionId)
    wx.request({
      url: url,
      header:{sessionId: that.data.sessionId},
      success: function (res) {
        that.setData({
          phone: res.data[0].phone,
          wechat: res.data[0].wechat
        })
        that.toggleDialog()
      },
      fail: function (res) {
        console.log('fail to get Contact_way', res)
      }
    })
  },
  /**
  * 控制 pop 的打开关闭
  * 该方法作用有两个:
  * 1：点击弹窗以外的位置可消失弹窗
  * 2：用到弹出或者关闭弹窗的业务逻辑时都可调用
  */
  toggleDialog: function() {
    this.setData({
      showDialog: !this.data.showDialog
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const sessionId = wx.getStorageSync('SessionId')
    if (sessionId) {
      this.setData({ sessionId: sessionId })
    }
    const json = wx.getStorageSync('detailJson')
    if (!json) {
      wx.showToast({
        title: '加载失败',
        icon: 'none',
        duration: 2000//持续的时间
      })
      wx.navigateBack({
          delta: 1
      })
    }
    if (options.id != 5) {
      this.setData({
        id: options.id,
        json: json
      })
    }
    else {
      var that = this
      that.setData({qid: json.qid,id: options.id})
      var url = baseUrl + 'Create/getQuestionairContent?id='
      url +=  that.data.qid
      console.log(url)

      wx.request({
        url: url,
        success: function (res) {
         // console.log(res)
          var content = JSON.parse(res.data[0].content)
          //console.log(content)
          var content_count = []
          for (var i = 0; i < content.length; i++) {
            content_count.push({type:content[i].type,a:0,b:0,c:0,d:0,fill:''})
          }
          //console.log(content_count)
          that.setData({
            content: content,
            content_count: content_count,
            description: res.data[0].description,
            name: res.data[0].name
          })
        },
        fail:function (res) {
          console.log('fail to get the questionnaire')
        }
      })
    }
    //console.log(this.data)
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})