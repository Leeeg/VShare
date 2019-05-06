package com.lee.vshare.model.net.entity;


import lee.com.netlibrary.net.revert.BaseResponseEntity;

/**
 * @describe
 */
public class BlogEntity extends BaseResponseEntity {

    /**
     * resultcode : 200
     * reason : successed!
     * result : {"sk":{"temp":"21","wind_direction":"东北风","wind_strength":"2级","humidity":"88%","time":"14:43"},"today":{"temperature":"17℃~22℃","weather":"大雨转中雨","weather_id":{"fa":"09","fb":"08"},"wind":"东风3-5级","week":"星期六","city":"深圳","date_y":"2019年04月13日","dressing_index":"较舒适","dressing_advice":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。","uv_index":"最弱","comfort_index":"","wash_index":"不宜","travel_index":"较不宜","exercise_index":"较不宜","drying_index":""},"future":{"day_20190413":{"temperature":"17℃~22℃","weather":"大雨转中雨","weather_id":{"fa":"09","fb":"08"},"wind":"东风3-5级","week":"星期六","date":"20190413"},"day_20190414":{"temperature":"19℃~24℃","weather":"大雨转小雨","weather_id":{"fa":"09","fb":"07"},"wind":"持续无风向微风","week":"星期日","date":"20190414"},"day_20190415":{"temperature":"19℃~24℃","weather":"中雨","weather_id":{"fa":"08","fb":"08"},"wind":"东风4-5级","week":"星期一","date":"20190415"},"day_20190416":{"temperature":"20℃~23℃","weather":"中雨","weather_id":{"fa":"08","fb":"08"},"wind":"持续无风向微风","week":"星期二","date":"20190416"},"day_20190417":{"temperature":"21℃~24℃","weather":"中雨转小雨","weather_id":{"fa":"08","fb":"07"},"wind":"持续无风向微风","week":"星期三","date":"20190417"},"day_20190418":{"temperature":"19℃~24℃","weather":"中雨","weather_id":{"fa":"08","fb":"08"},"wind":"东风4-5级","week":"星期四","date":"20190418"},"day_20190419":{"temperature":"19℃~24℃","weather":"大雨转小雨","weather_id":{"fa":"09","fb":"07"},"wind":"持续无风向微风","week":"星期五","date":"20190419"}}}
     * error_code : 0
     */

    private String resultcode;
    private String reason;
    private ResultBean result;
    private int error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * sk : {"temp":"21","wind_direction":"东北风","wind_strength":"2级","humidity":"88%","time":"14:43"}
         * today : {"temperature":"17℃~22℃","weather":"大雨转中雨","weather_id":{"fa":"09","fb":"08"},"wind":"东风3-5级","week":"星期六","city":"深圳","date_y":"2019年04月13日","dressing_index":"较舒适","dressing_advice":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。","uv_index":"最弱","comfort_index":"","wash_index":"不宜","travel_index":"较不宜","exercise_index":"较不宜","drying_index":""}
         * future : {"day_20190413":{"temperature":"17℃~22℃","weather":"大雨转中雨","weather_id":{"fa":"09","fb":"08"},"wind":"东风3-5级","week":"星期六","date":"20190413"},"day_20190414":{"temperature":"19℃~24℃","weather":"大雨转小雨","weather_id":{"fa":"09","fb":"07"},"wind":"持续无风向微风","week":"星期日","date":"20190414"},"day_20190415":{"temperature":"19℃~24℃","weather":"中雨","weather_id":{"fa":"08","fb":"08"},"wind":"东风4-5级","week":"星期一","date":"20190415"},"day_20190416":{"temperature":"20℃~23℃","weather":"中雨","weather_id":{"fa":"08","fb":"08"},"wind":"持续无风向微风","week":"星期二","date":"20190416"},"day_20190417":{"temperature":"21℃~24℃","weather":"中雨转小雨","weather_id":{"fa":"08","fb":"07"},"wind":"持续无风向微风","week":"星期三","date":"20190417"},"day_20190418":{"temperature":"19℃~24℃","weather":"中雨","weather_id":{"fa":"08","fb":"08"},"wind":"东风4-5级","week":"星期四","date":"20190418"},"day_20190419":{"temperature":"19℃~24℃","weather":"大雨转小雨","weather_id":{"fa":"09","fb":"07"},"wind":"持续无风向微风","week":"星期五","date":"20190419"}}
         */

        private SkBean sk;
        private TodayBean today;
        private FutureBean future;

        public SkBean getSk() {
            return sk;
        }

        public void setSk(SkBean sk) {
            this.sk = sk;
        }

        public TodayBean getToday() {
            return today;
        }

        public void setToday(TodayBean today) {
            this.today = today;
        }

        public FutureBean getFuture() {
            return future;
        }

        public void setFuture(FutureBean future) {
            this.future = future;
        }

        public static class SkBean {
            /**
             * temp : 21
             * wind_direction : 东北风
             * wind_strength : 2级
             * humidity : 88%
             * time : 14:43
             */

            private String temp;
            private String wind_direction;
            private String wind_strength;
            private String humidity;
            private String time;

            public String getTemp() {
                return temp;
            }

            public void setTemp(String temp) {
                this.temp = temp;
            }

            public String getWind_direction() {
                return wind_direction;
            }

            public void setWind_direction(String wind_direction) {
                this.wind_direction = wind_direction;
            }

            public String getWind_strength() {
                return wind_strength;
            }

            public void setWind_strength(String wind_strength) {
                this.wind_strength = wind_strength;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class TodayBean {
            /**
             * temperature : 17℃~22℃
             * weather : 大雨转中雨
             * weather_id : {"fa":"09","fb":"08"}
             * wind : 东风3-5级
             * week : 星期六
             * city : 深圳
             * date_y : 2019年04月13日
             * dressing_index : 较舒适
             * dressing_advice : 建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。
             * uv_index : 最弱
             * comfort_index :
             * wash_index : 不宜
             * travel_index : 较不宜
             * exercise_index : 较不宜
             * drying_index :
             */

            private String temperature;
            private String weather;
            private WeatherIdBean weather_id;
            private String wind;
            private String week;
            private String city;
            private String date_y;
            private String dressing_index;
            private String dressing_advice;
            private String uv_index;
            private String comfort_index;
            private String wash_index;
            private String travel_index;
            private String exercise_index;
            private String drying_index;

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public WeatherIdBean getWeather_id() {
                return weather_id;
            }

            public void setWeather_id(WeatherIdBean weather_id) {
                this.weather_id = weather_id;
            }

            public String getWind() {
                return wind;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDate_y() {
                return date_y;
            }

            public void setDate_y(String date_y) {
                this.date_y = date_y;
            }

            public String getDressing_index() {
                return dressing_index;
            }

            public void setDressing_index(String dressing_index) {
                this.dressing_index = dressing_index;
            }

            public String getDressing_advice() {
                return dressing_advice;
            }

            public void setDressing_advice(String dressing_advice) {
                this.dressing_advice = dressing_advice;
            }

            public String getUv_index() {
                return uv_index;
            }

            public void setUv_index(String uv_index) {
                this.uv_index = uv_index;
            }

            public String getComfort_index() {
                return comfort_index;
            }

            public void setComfort_index(String comfort_index) {
                this.comfort_index = comfort_index;
            }

            public String getWash_index() {
                return wash_index;
            }

            public void setWash_index(String wash_index) {
                this.wash_index = wash_index;
            }

            public String getTravel_index() {
                return travel_index;
            }

            public void setTravel_index(String travel_index) {
                this.travel_index = travel_index;
            }

            public String getExercise_index() {
                return exercise_index;
            }

            public void setExercise_index(String exercise_index) {
                this.exercise_index = exercise_index;
            }

            public String getDrying_index() {
                return drying_index;
            }

            public void setDrying_index(String drying_index) {
                this.drying_index = drying_index;
            }

            @Override
            public String toString() {
                return "TodayBean{" +
                        "temperature='" + temperature + '\'' +
                        ", weather='" + weather + '\'' +
                        ", weather_id=" + weather_id +
                        ", wind='" + wind + '\'' +
                        ", week='" + week + '\'' +
                        ", city='" + city + '\'' +
                        ", date_y='" + date_y + '\'' +
                        ", dressing_index='" + dressing_index + '\'' +
                        ", dressing_advice='" + dressing_advice + '\'' +
                        ", uv_index='" + uv_index + '\'' +
                        ", comfort_index='" + comfort_index + '\'' +
                        ", wash_index='" + wash_index + '\'' +
                        ", travel_index='" + travel_index + '\'' +
                        ", exercise_index='" + exercise_index + '\'' +
                        ", drying_index='" + drying_index + '\'' +
                        '}';
            }

            public static class WeatherIdBean {
                /**
                 * fa : 09
                 * fb : 08
                 */

                private String fa;
                private String fb;

                public String getFa() {
                    return fa;
                }

                public void setFa(String fa) {
                    this.fa = fa;
                }

                public String getFb() {
                    return fb;
                }

                public void setFb(String fb) {
                    this.fb = fb;
                }
            }
        }

        public static class FutureBean {
            /**
             * day_20190413 : {"temperature":"17℃~22℃","weather":"大雨转中雨","weather_id":{"fa":"09","fb":"08"},"wind":"东风3-5级","week":"星期六","date":"20190413"}
             * day_20190414 : {"temperature":"19℃~24℃","weather":"大雨转小雨","weather_id":{"fa":"09","fb":"07"},"wind":"持续无风向微风","week":"星期日","date":"20190414"}
             * day_20190415 : {"temperature":"19℃~24℃","weather":"中雨","weather_id":{"fa":"08","fb":"08"},"wind":"东风4-5级","week":"星期一","date":"20190415"}
             * day_20190416 : {"temperature":"20℃~23℃","weather":"中雨","weather_id":{"fa":"08","fb":"08"},"wind":"持续无风向微风","week":"星期二","date":"20190416"}
             * day_20190417 : {"temperature":"21℃~24℃","weather":"中雨转小雨","weather_id":{"fa":"08","fb":"07"},"wind":"持续无风向微风","week":"星期三","date":"20190417"}
             * day_20190418 : {"temperature":"19℃~24℃","weather":"中雨","weather_id":{"fa":"08","fb":"08"},"wind":"东风4-5级","week":"星期四","date":"20190418"}
             * day_20190419 : {"temperature":"19℃~24℃","weather":"大雨转小雨","weather_id":{"fa":"09","fb":"07"},"wind":"持续无风向微风","week":"星期五","date":"20190419"}
             */

            private Day20190413Bean day_20190413;
            private Day20190414Bean day_20190414;
            private Day20190415Bean day_20190415;
            private Day20190416Bean day_20190416;
            private Day20190417Bean day_20190417;
            private Day20190418Bean day_20190418;
            private Day20190419Bean day_20190419;

            public Day20190413Bean getDay_20190413() {
                return day_20190413;
            }

            public void setDay_20190413(Day20190413Bean day_20190413) {
                this.day_20190413 = day_20190413;
            }

            public Day20190414Bean getDay_20190414() {
                return day_20190414;
            }

            public void setDay_20190414(Day20190414Bean day_20190414) {
                this.day_20190414 = day_20190414;
            }

            public Day20190415Bean getDay_20190415() {
                return day_20190415;
            }

            public void setDay_20190415(Day20190415Bean day_20190415) {
                this.day_20190415 = day_20190415;
            }

            public Day20190416Bean getDay_20190416() {
                return day_20190416;
            }

            public void setDay_20190416(Day20190416Bean day_20190416) {
                this.day_20190416 = day_20190416;
            }

            public Day20190417Bean getDay_20190417() {
                return day_20190417;
            }

            public void setDay_20190417(Day20190417Bean day_20190417) {
                this.day_20190417 = day_20190417;
            }

            public Day20190418Bean getDay_20190418() {
                return day_20190418;
            }

            public void setDay_20190418(Day20190418Bean day_20190418) {
                this.day_20190418 = day_20190418;
            }

            public Day20190419Bean getDay_20190419() {
                return day_20190419;
            }

            public void setDay_20190419(Day20190419Bean day_20190419) {
                this.day_20190419 = day_20190419;
            }

            public static class Day20190413Bean {
                /**
                 * temperature : 17℃~22℃
                 * weather : 大雨转中雨
                 * weather_id : {"fa":"09","fb":"08"}
                 * wind : 东风3-5级
                 * week : 星期六
                 * date : 20190413
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanX {
                    /**
                     * fa : 09
                     * fb : 08
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20190414Bean {
                /**
                 * temperature : 19℃~24℃
                 * weather : 大雨转小雨
                 * weather_id : {"fa":"09","fb":"07"}
                 * wind : 持续无风向微风
                 * week : 星期日
                 * date : 20190414
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXX {
                    /**
                     * fa : 09
                     * fb : 07
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20190415Bean {
                /**
                 * temperature : 19℃~24℃
                 * weather : 中雨
                 * weather_id : {"fa":"08","fb":"08"}
                 * wind : 东风4-5级
                 * week : 星期一
                 * date : 20190415
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXXX {
                    /**
                     * fa : 08
                     * fb : 08
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20190416Bean {
                /**
                 * temperature : 20℃~23℃
                 * weather : 中雨
                 * weather_id : {"fa":"08","fb":"08"}
                 * wind : 持续无风向微风
                 * week : 星期二
                 * date : 20190416
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXXXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXXXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXXXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXXXX {
                    /**
                     * fa : 08
                     * fb : 08
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20190417Bean {
                /**
                 * temperature : 21℃~24℃
                 * weather : 中雨转小雨
                 * weather_id : {"fa":"08","fb":"07"}
                 * wind : 持续无风向微风
                 * week : 星期三
                 * date : 20190417
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXXXXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXXXXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXXXXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXXXXX {
                    /**
                     * fa : 08
                     * fb : 07
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20190418Bean {
                /**
                 * temperature : 19℃~24℃
                 * weather : 中雨
                 * weather_id : {"fa":"08","fb":"08"}
                 * wind : 东风4-5级
                 * week : 星期四
                 * date : 20190418
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXXXXXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXXXXXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXXXXXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXXXXXX {
                    /**
                     * fa : 08
                     * fb : 08
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20190419Bean {
                /**
                 * temperature : 19℃~24℃
                 * weather : 大雨转小雨
                 * weather_id : {"fa":"09","fb":"07"}
                 * wind : 持续无风向微风
                 * week : 星期五
                 * date : 20190419
                 */

                private String temperature;
                private String weather;
                private WeatherIdBeanXXXXXXX weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBeanXXXXXXX getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBeanXXXXXXX weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBeanXXXXXXX {
                    /**
                     * fa : 09
                     * fb : 07
                     */

                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }
        }
    }
}
